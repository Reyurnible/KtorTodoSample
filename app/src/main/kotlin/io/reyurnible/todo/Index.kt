package io.reyurnible.todo

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.index() {
    get {
        call.respondText { "Index Page" }
    }
}