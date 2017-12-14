package io.reyurnible.app

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.features.PartialContentSupport
import io.ktor.locations.Locations
import io.ktor.locations.location
import io.ktor.routing.Routing
import io.reyurnible.app.resource.index

@location("/")
class Index

class TodoApp {

    fun Application.install() {
        install(DefaultHeaders)
        install(CallLogging)
        install(ConditionalHeaders)
        install(PartialContentSupport)
        install(Locations)

        install(Routing) {
            index()
        }
    }

}