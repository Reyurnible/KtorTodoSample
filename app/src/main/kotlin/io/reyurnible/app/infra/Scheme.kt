package io.reyurnible.app.infra

import org.jetbrains.squash.definition.*

val schemes
    get() = listOf(TodoTable)

// Create Table for
object TodoTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", 50)
    val isDone = bool("done")
    val timestamp = varchar("timestamp", 50)
}