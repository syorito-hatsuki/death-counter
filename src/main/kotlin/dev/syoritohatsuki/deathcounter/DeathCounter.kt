package dev.syoritohatsuki.deathcounter

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.event.PlayerDeathEvents
import dev.syoritohatsuki.deathcounter.network.DEATHS
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import io.netty.buffer.Unpooled
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.stat.Stats
import org.slf4j.Logger

object DeathCounter : ModInitializer {

    private val serverLogger: Logger = LogUtils.getLogger()

    const val MOD_ID = "deathcounter"

    override fun onInitialize() {
        DuckyUpdater.checkForUpdate("7x0zk3YH", MOD_ID)

        ServerLifecycleEvents.SERVER_STARTED.register(
            ServerLifecycleEvents.ServerStarted { server ->

                serverLogger.info("Server started")

                ServerPlayNetworking.registerGlobalReceiver(DEATHS) { _, _, _, _, _ -> }

                PlayerDeathEvents.DEATH.register(PlayerDeathEvents.Death { player ->
                    val playerName = player.entityName
                    val deathCount = player.statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))

                    serverLogger.info("Server: $playerName, $deathCount")

                    server.playerManager.playerList.forEach { serverPlayer ->
                        ServerPlayNetworking.send(
                            serverPlayer, DEATHS, PacketByteBuf(Unpooled.buffer())
                                .writeString("$playerName,$deathCount")
                        )
                    }
                })
            })
    }
}