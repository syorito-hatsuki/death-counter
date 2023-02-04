package dev.syoritohatsuki.deathcounter

import com.mojang.logging.LogUtils
import kotlinx.serialization.json.Json
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted
import org.slf4j.Logger

object DeathCounter : ModInitializer {

    val mainLogger: Logger = LogUtils.getLogger()
    val json = Json { encodeDefaults = true; prettyPrint = true; ignoreUnknownKeys = true }

    override fun onInitialize() {
//        lateinit var webServer: ApplicationEngine

        mainLogger.info("${javaClass.simpleName} initialized")

        ServerLifecycleEvents.SERVER_STARTED.register(ServerStarted {
            mainLogger.info("Server is dedicate? ${it.isDedicated}")
        })

//        ServerPlayerEvents.ALLOW_DEATH.register(ServerPlayerEvents.AllowDeath { player, _, _ ->
//            DeathManager.writeDeath(
//                player.entityName,
//                (player.statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) + 1)
//            )
//            return@AllowDeath true
//        })
//
//        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
//            DeathListCommand.register(dispatcher)
//        })

    }
}