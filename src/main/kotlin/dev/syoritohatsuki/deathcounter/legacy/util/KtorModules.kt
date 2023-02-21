package dev.syoritohatsuki.deathcounter.legacy.util

import dev.syoritohatsuki.deathcounter.legacy.client.manager.ClientConfigManager
import dev.syoritohatsuki.deathcounter.legacy.manager.DeathManager
import dev.syoritohatsuki.deathcounter.legacy.server.DeathCounterServer.serverLogger
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.serverModule() {
    install(CORS) { anyHost() }
    install(ContentNegotiation) { json(ClientConfigManager.json) }
    routing {
        get {
            call.respond(DeathManager.deaths())
        }
        get("/{username}") {
            call.respond(DeathManager.getPlayerDeathCount(call.parameters["username"]!!))
        }
    }
    serverLogger.info("${javaClass.simpleName} WebServer started")
}