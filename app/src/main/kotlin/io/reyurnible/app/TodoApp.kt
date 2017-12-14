package io.reyurnible.app

import com.google.gson.GsonBuilder
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import io.reyurnible.app.controller.index
import io.reyurnible.app.controller.todo
import io.reyurnible.app.infra.Database

val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

class TodoApp {

    // Call from application.conf setting
    fun Application.install() {
        val database = Database(this)

        install(DefaultHeaders)
        install(CallLogging)
        install(ConditionalHeaders)
        install(PartialContentSupport)
        install(ContentNegotiation) {
            register(ContentType.Application.Json, GsonConverter(gson))
        }
        install(Locations) // @locationしたroutingを読み取ってる
        install(Routing) {
            index(database)
            todo(database)
        }
    }

}