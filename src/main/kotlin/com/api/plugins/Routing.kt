package com.api.plugins

import com.api.model.Item
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val items = mutableListOf(Item(1, "Item 1"), Item(2, "Item 2"))

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/items") {
            get {
                call.respond(com.api.plugins.items)
            }

            post {
                val newItem = call.receive<Item>()
                com.api.plugins.items.add(newItem)
                call.respond(HttpStatusCode.Created, newItem)
            }
        }
    }
}
