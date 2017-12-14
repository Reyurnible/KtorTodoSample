package io.reyurnible.app.controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.location
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.reyurnible.app.infra.Database

@location("/todo")
class Todo

fun Route.todo(database: Database) {
    get {
//        val todoList = database.getAllTodo()
//        call.respond(todoList)
        call.respond(CreateTodoBody().apply { title ="/todo" })
    }
    post {
        val body: CreateTodoBody = call.receive<CreateTodoBody>()
        val title: String = body.title ?: return@post call.respond(HttpStatusCode.BadRequest)
        database.createTodo(title, body.isDone)
        call.respond(HttpStatusCode.Created)
    }
}

class CreateTodoBody {
    var title: String? = null
    var isDone: Boolean = false
}