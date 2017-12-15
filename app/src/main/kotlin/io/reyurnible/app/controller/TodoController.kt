package io.reyurnible.app.controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.reyurnible.app.infra.Database

fun Route.todo(database: Database) = route("/todo") {
    get {
        val todoList = database.getAllTodo()
        call.respond(todoList)
    }
    post("/create") {
        val body = call.receive<CreateTodoBody>()
        val title: String = body.title ?: return@post call.respond(HttpStatusCode.BadRequest)
        database.createTodo(title, body.isDone)
        call.respond(HttpStatusCode.Created)
    }
}

class CreateTodoBody {
    var title: String? = null
    var isDone: Boolean = false
}