package dev.syoritohatsuki.deathcounter

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.command.DeathListCommand
import dev.syoritohatsuki.deathcounter.manager.DeathManager
import dev.syoritohatsuki.deathcounter.manager.DeathManager.deaths
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted
import net.minecraft.stat.Stats
import org.slf4j.Logger
import java.util.concurrent.TimeUnit

object DeathCounter : ModInitializer {

    val logger: Logger = LogUtils.getLogger()
    val json = Json { encodeDefaults = true; prettyPrint = true; ignoreUnknownKeys = true }

    override fun onInitialize() {
        lateinit var webServer: ApplicationEngine

        logger.info("${javaClass.simpleName} initialized")

        ServerLifecycleEvents.SERVER_STARTED.register(ServerStarted {
            webServer = embeddedServer(CIO, port = 1540, host = getServerIp(it.serverIp)) {
                install(CORS) { anyHost() }
                install(ContentNegotiation) { json(json) }
                routing {
                    get { call.respond(deaths()) }
                    get("/{username}") {
                        call.respond(DeathManager.getPlayerDeathCount(call.parameters["username"]!!))
                    }
                }
                logger.info("${javaClass.simpleName} WebServer started")
            }.start()
        })

        ServerLifecycleEvents.SERVER_STOPPING.register(ServerLifecycleEvents.ServerStopping {
            webServer.stop(1, 5, TimeUnit.SECONDS)
            logger.info("${javaClass.simpleName} WebServer stopped")
        })

        ServerPlayerEvents.ALLOW_DEATH.register(ServerPlayerEvents.AllowDeath { player, _, _ ->
            DeathManager.writeDeath(
                player.entityName,
                (player.statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) + 1)
            )
            return@AllowDeath true
        })

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            DeathListCommand.register(dispatcher)
        })
    }

    private fun getServerIp(serverIp: String?): String = when (serverIp) {
        null -> "0.0.0.0"
        "" -> "0.0.0.0"
        else -> serverIp
    }
}