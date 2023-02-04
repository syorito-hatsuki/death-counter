package dev.syoritohatsuki.deathcounter.util

import dev.syoritohatsuki.deathcounter.DeathCounter
import dev.syoritohatsuki.deathcounter.DeathCounterServer
import dev.syoritohatsuki.deathcounter.client.manager.ClientConfigManager
import dev.syoritohatsuki.deathcounter.manager.DeathManager
import freemarker.cache.ClassTemplateLoader
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.text.Text
import net.minecraft.text.TextColor

fun Application.clientModule(handler: ClientPlayNetworkHandler, client: MinecraftClient) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    routing {
        get {
            call.respond(
                FreeMarkerContent(
                    "index.ftl", mapOf(
                        "player" to client.player?.entityName,
                        "delay" to ClientConfigManager.read().msDelay.toString(),
                        "remoteIp" to handler.connection.address.isValidIPAddress()
                    )
                )
            )
        }
        get("/{playerName}") {
            call.respond(
                FreeMarkerContent(
                    "index.ftl", mapOf(
                        "player" to call.parameters["playerName"],
                        "delay" to ClientConfigManager.read().msDelay.toString(),
                        "remoteIp" to handler.connection.address.isValidIPAddress()
                    )
                )
            )
        }
    }
    client.player?.let { clientPlayerEntity ->
        clientPlayerEntity.sendMessage(Text.literal(""))
        clientPlayerEntity.sendMessage(Text.literal("§a§lWebUI Started"))
        clientPlayerEntity.sendMessage(Text.literal("§7Access URL: §9§nhttp://0.0.0.0:${ClientConfigManager.read().port}"))
        clientPlayerEntity.sendMessage(Text.literal(""))

        /*   Styling text   */

        clientPlayerEntity.sendMessage(Text.translatable("").styled {
            it.withColor(TextColor.fromRgb(0))
        })
    }
}


fun Application.serverModule() {
    install(CORS) { anyHost() }
    install(ContentNegotiation) { json(DeathCounter.json) }
    routing {
        get {
            call.respond(DeathManager.deaths())
        }
        get("/{username}") {
            call.respond(DeathManager.getPlayerDeathCount(call.parameters["username"]!!))
        }
    }
    DeathCounterServer.serverLogger.info("${javaClass.simpleName} WebServer started")
}