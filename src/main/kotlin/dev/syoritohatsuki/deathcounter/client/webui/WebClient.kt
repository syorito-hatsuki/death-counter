package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import net.minecraft.client.MinecraftClient
import java.util.concurrent.TimeUnit

object WebClient {

    private lateinit var webClient: EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>

    fun startWebClient(client: MinecraftClient) {
        webClient = embeddedServer(
            factory = CIO,
            host = ClientConfigManager.read().webSetup.localAddress,
            port = ClientConfigManager.read().webSetup.servicePort,
            module = {
                clientModule(client)
            }).start()
    }

    fun stopWebClient() = webClient.stop(1, 5, TimeUnit.SECONDS)
}
