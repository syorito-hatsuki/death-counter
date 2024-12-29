package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import dev.syoritohatsuki.deathcounter.client.extension.getDeathCount
import dev.syoritohatsuki.deathcounter.client.toast.WebToast
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import net.minecraft.client.MinecraftClient

fun Application.clientModule(client: MinecraftClient) {

    var host = ""
    var port = 0

    monitor.subscribe(ApplicationStarted) {
        launch {
            val connector = engine.resolvedConnectors().firstOrNull()
            if (connector != null) {
                host = connector.host
                port = connector.port

                if (!ClientConfigManager.read().showToastNotification.disable) {
                    client.toastManager.add(WebToast(host, port))
                }
            } else {
                log.error("No connectors found!")
            }
        }
    }


    routing {
        get {
            call.respondHtml {
                htmlTemplate(host, port)
            }
        }

        get("deaths") {
            call.respondText(client.player?.getDeathCount().toString())
        }
    }
}