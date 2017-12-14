package io.reyurnible.app.infra

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.log
import io.reyurnible.app.model.Todo
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.run
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.query.select
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import org.joda.time.DateTime
import kotlin.coroutines.experimental.CoroutineContext

class Database(application: Application) {
    private val dispatcher: CoroutineContext
    private val connectionPool: HikariDataSource
    private val connection: DatabaseConnection

    init {
        val config = application.environment.config.config("database")
        val url = config.property("connection").getString()
        val poolSize = config.property("poolSize").getString().toInt()
        application.log.info("Connecting to database at '$url'")

        dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")
        val cfg = HikariConfig()
        cfg.jdbcUrl = url
        cfg.maximumPoolSize = poolSize
        cfg.validate()

        connectionPool = HikariDataSource(cfg)

        connection = H2Connection { connectionPool.connection }
        connection.transaction {
            databaseSchema().create(schemes)
        }
    }

    suspend fun createTodo(title: String, isDone: Boolean = false) = run(dispatcher) {
        connection.transaction {
            insertInto(TodoTable).values {
                it[TodoTable.title] = title
                it[TodoTable.isDone] = isDone
                it[TodoTable.timestamp] = DateTime.now().toString()
            }.execute()
        }
    }

    // Coroutines function
    suspend fun getAllTodo(): List<Todo> = run(dispatcher) {
        connection.transaction {
            TodoTable.select().execute().map { dao ->
                Todo(
                        id = dao[TodoTable.id],
                        title = dao[TodoTable.title],
                        isDone = dao[TodoTable.isDone],
                        timestamp = dao[TodoTable.timestamp].let(DateTime::parse)
                )
            }.toList()
        }
    }

}