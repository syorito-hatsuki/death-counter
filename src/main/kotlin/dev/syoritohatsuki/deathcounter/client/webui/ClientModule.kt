package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.legacy.client.manager.ClientConfigManager
import dev.syoritohatsuki.deathcounter.legacy.util.isValidIPAddress
import freemarker.cache.ClassTemplateLoader
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.text.Text

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
        get {
            call.respondHtml { htmlTemplate("", "", 1) }
        }
    }

    /*   TODO Change chat message to Toast notification   */
    client.player?.let { clientPlayerEntity ->
        clientPlayerEntity.sendMessage(Text.literal(""))
        clientPlayerEntity.sendMessage(Text.literal("§a§lWebUI Started"))
        clientPlayerEntity.sendMessage(Text.literal("§7Access URL: §9§nhttp://0.0.0.0:${ClientConfigManager.read().port}"))
        clientPlayerEntity.sendMessage(Text.literal(""))
    }
}
