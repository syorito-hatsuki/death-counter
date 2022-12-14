package dev.syoritohatsuki.deathcounter.client

import dev.syoritohatsuki.deathcounter.DeathCounter.logger
import dev.syoritohatsuki.deathcounter.client.manager.ClientConfigManager.read
import dev.syoritohatsuki.deathcounter.util.NetworkUtil
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
import net.minecraft.text.Text
import java.util.concurrent.TimeUnit

@Environment(EnvType.CLIENT)
object DeathCounterClient : ClientModInitializer {
    override fun onInitializeClient() {

        lateinit var webClient: ApplicationEngine

        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { handler, _, client ->
            webClient = embeddedServer(CIO, host = "0.0.0.0", port = read().port) {
                install(FreeMarker) {
                    templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
                }
                routing {
                    get {
                        call.respond(
                            FreeMarkerContent(
                                "index.ftl", mapOf(
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
                                "index.ftl", mapOf(
                                    "player" to call.parameters["playerName"],
                                    "delay" to read().msDelay.toString(),
                                    "remoteIp" to handler.getHostName()
                                )
                            )
                        )
                    }
                }
                client.player?.sendMessage(Text.literal(""))
                client.player?.sendMessage(Text.literal("??a??lWebUI Started"))
                client.player?.sendMessage(Text.literal("??7Access URL: ??9??nhttp://0.0.0.0:${read().port}"))
                client.player?.sendMessage(Text.literal(""))
            }.start()
        })

        ClientPlayConnectionEvents.DISCONNECT.register(ClientPlayConnectionEvents.Disconnect { _, _ ->
            webClient.stop(1, 5, TimeUnit.SECONDS)
            logger.info("${javaClass.simpleName} WebClient stopped")
        })
    }

    private fun ClientPlayNetworkHandler.getHostName(): String = NetworkUtil.isValidIPAddress(connection.address)
}