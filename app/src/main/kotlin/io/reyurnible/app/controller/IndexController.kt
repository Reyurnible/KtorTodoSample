package io.reyurnible.app.controller

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.reyurnible.app.infra.Database

// Indexの実装
fun Route.index(database: Database) = route("/") {
    get {
        call.respondText { "Index Page" }
    }
}