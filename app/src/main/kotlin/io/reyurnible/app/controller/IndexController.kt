package io.reyurnible.app.controller

import io.ktor.application.call
import io.ktor.locations.location
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.reyurnible.app.infra.Database

@location("/")
class Index

// Indexの実装
fun Route.index(database: Database) {
    get {
        call.respondText { "Index Page" }
    }
}