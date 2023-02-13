package dev.syoritohatsuki.deathcounter

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.client.DeathCounterClient
import dev.syoritohatsuki.deathcounter.event.PlayerDeathCallback
import dev.syoritohatsuki.deathcounter.network.DEATHS
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.stat.Stats
import org.slf4j.Logger


object DeathCounter : ModInitializer {

    const val MOD_ID = "deathcounter"

    val serverLogger: Logger = LogUtils.getLogger()

    override fun onInitialize() {
        DuckyUpdater.checkForUpdate("7x0zk3YH", MOD_ID)

        ServerLifecycleEvents.SERVER_STARTED.register(
            ServerLifecycleEvents.ServerStarted { server ->

                serverLogger.info("Server started")

                ServerPlayNetworking.registerGlobalReceiver(DEATHS) { _, _, _, _, _ -> }

                PlayerDeathCallback.DEATH.register(object : PlayerDeathCallback.PlayerDeath {
                    override fun onDie(player: ServerPlayerEntity) {
                        val playerName = player.entityName
                        val deathCount = player.statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))

                        DeathCounterClient.clientLogger.info("Server: $playerName, $deathCount")

                        server.playerManager.playerList.forEach { serverPlayer ->
                            ServerPlayNetworking.send(
                                serverPlayer,
                                DEATHS,
                                PacketByteBufs
                                    .create()
                                    .writeString("$playerName,$deathCount")
                            )
                        }
                    }
                })
            })
    }
}