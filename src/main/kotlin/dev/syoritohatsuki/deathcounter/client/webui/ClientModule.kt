package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import dev.syoritohatsuki.deathcounter.client.extension.getDeathCount
import dev.syoritohatsuki.deathcounter.client.toast.WebToast
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.minecraft.client.MinecraftClient

fun Application.clientModule(client: MinecraftClient) {
    (environment as ApplicationEngineEnvironment).connectors.also { con ->

        environment.monitor.subscribe(ApplicationStarted) {
            if (!ClientConfigManager.read().showToastNotification.disable) WebToast.show(
                client.toastManager,
                con[0].host,
                con[0].port
            )
        }

        routing {
            get {
                call.respondHtml { htmlTemplate(con[0].host, con[0].port) }
            }

            get("deaths") {
                call.respondText(client.player?.getDeathCount().toString())
            }
        }
    }
}
