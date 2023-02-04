package dev.syoritohatsuki.deathcounter.client

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.client.manager.ClientConfigManager.read
import dev.syoritohatsuki.deathcounter.util.clientModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.freemarker.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.network.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import org.slf4j.Logger
import java.util.concurrent.TimeUnit

@Environment(EnvType.CLIENT)
object DeathCounterClient : ClientModInitializer {

    val clientLogger: Logger = LogUtils.getLogger()

    override fun onInitializeClient() {

        lateinit var webClient: ApplicationEngine

        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { handler, _, client ->
            clientLogger.info("Server is dedicate? ${client.server?.isDedicated}")
            clientLogger.info("Server is dedicate? ${client.currentServerEntry?.isLocal}")
            webClient = embeddedServer(
                CIO,
                host = "0.0.0.0",
                port = read().port,
                module = {
                    clientModule(handler, client)
                }
            ).start()
        })

        ClientPlayConnectionEvents.DISCONNECT.register(ClientPlayConnectionEvents.Disconnect { _, _ ->
            webClient.stop(1, 5, TimeUnit.SECONDS)
            clientLogger.info("${javaClass.simpleName} WebClient stopped")
        })
    }
}