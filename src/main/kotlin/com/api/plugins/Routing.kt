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

            get("/{id}") {
                val itemId = call.parameters["id"]?.toIntOrNull()
                if (itemId != null) {
                    val item = com.api.plugins.items.find { it.id == itemId }
                    if (item != null) {
                        call.respond(item)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            put("/{id}") {
                val itemId = call.parameters["id"]?.toIntOrNull()
                if (itemId != null) {
                    val updatedItem = call.receive<Item>()
                    val existingItemIndex = com.api.plugins.items.indexOfFirst { it.id == itemId }
                    if (existingItemIndex != -1) {
                        com.api.plugins.items[existingItemIndex] = updatedItem
                        call.respond(updatedItem)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val itemId = call.parameters["id"]?.toIntOrNull()
                if (itemId != null) {
                    val removedItem = com.api.plugins.items.removeIf { it.id == itemId }
                    if (removedItem) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}
