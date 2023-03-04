package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.legacy.client.manager.ClientConfigManager
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import net.minecraft.client.MinecraftClient
import java.util.concurrent.TimeUnit

object WebClient {

    private lateinit var webClient: ApplicationEngine
    fun startWebClient(client: MinecraftClient) {
        webClient = embeddedServer(
            CIO,
            host = "0.0.0.0",
            port = ClientConfigManager.read().port,
            module = {
                clientModule(client)
            }
        ).start()
    }

    fun stopWebClient() {
        webClient.stop(1, 5, TimeUnit.SECONDS)
    }
}