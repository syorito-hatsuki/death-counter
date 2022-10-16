package dev.syoritohatsuki.deathcounter.client

import dev.syoritohatsuki.deathcounter.DeathCounter.logger
import dev.syoritohatsuki.deathcounter.client.manager.ClientConfigManager.read
import freemarker.cache.ClassTemplateLoader
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
import net.minecraft.client.network.ClientPlayNetworkHandler
import java.util.concurrent.TimeUnit

@Environment(EnvType.CLIENT)
object DeathCounterClient : ClientModInitializer {
    override fun onInitializeClient() {

        lateinit var webClient: ApplicationEngine

        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { handler, sender, client ->

            webClient = embeddedServer(CIO, port = read().port, host = read().ip) {
                install(FreeMarker) {
                    templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
                }
                routing {
                    get {
                        call.respond(
                            FreeMarkerContent(
                                "index.ftl",
                                mapOf(
                                    "player" to client.player?.entityName,
                                    "delay" to read().msDelay.toString(),
                                    "remoteIp" to handler.getHostName()
                                )
                            )
                        )
                    }
                    get("/{playerName}") {
                        call.respond(
                            FreeMarkerContent(
                                "index.ftl",
                                mapOf(
                                    "player" to call.parameters["playerName"],
                                    "delay" to read().msDelay.toString(),
                                    "remoteIp" to handler.getHostName()
                                )
                            )
                        )
                    }
                }
            }.start()
        })

        ClientPlayConnectionEvents.DISCONNECT.register(ClientPlayConnectionEvents.Disconnect { _, _ ->
            webClient.stop(1, 5, TimeUnit.SECONDS)
            logger.info("${javaClass.simpleName} WebClient stopped")
        })
    }

    private fun ClientPlayNetworkHandler.getHostName(): String {
        val ip = this.connection.address.hostname.split('.')[0].replace('-', '.')
        return if (ip != "" && ip != "localhost") ip else "0.0.0.0"
    }
}