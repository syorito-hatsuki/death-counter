package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.legacy.client.manager.ClientConfigManager
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

fun Application.clientModule(client: MinecraftClient) {
    routing {
        get("deaths/{playerName}") {
            /*   TODO respond player death count from cache file    */
        }
        get("/{playerName}") {
            call.respondHtml { htmlTemplate(call.parameters["playerName"].toString()) }
        }
        get {
            call.respondHtml { htmlTemplate(client.player?.entityName.toString()) }
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
