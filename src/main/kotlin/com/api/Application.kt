package com.api

import com.api.plugins.configureRouting
import com.api.plugins.configureSerialization
import com.api.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()

    Database.connect(
        url = "jdbc:postgresql://localhost:5432/ktor",
        driver = "org.postgresql.Driver",
        user = "ktor",
        password = "test"
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(ItemsTable)
    }
}
