package dev.syoritohatsuki.deathcounter

import dev.syoritohatsuki.deathcounter.command.serverSideCommands
import dev.syoritohatsuki.deathcounter.event.PlayerDeathEvents
import dev.syoritohatsuki.deathcounter.network.ON_DEATH
import dev.syoritohatsuki.deathcounter.util.CacheManager
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.stat.Stats

object DeathCounter : ModInitializer {

    const val MOD_ID = "deathcounter"

    override fun onInitialize() {
        DuckyUpdater.checkForUpdate("7x0zk3YH", MOD_ID)

        ServerPlayNetworking.registerGlobalReceiver(ON_DEATH) { _, _, _, _, _ -> }

        PlayerDeathEvents.DEATH.register(PlayerDeathEvents.Death { player ->
            CacheManager.addOrUpdatePlayer(player.entityName, player.getDeathCount())
        })

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _ ->
            dispatcher.serverSideCommands()
        })

        ServerPlayConnectionEvents.JOIN.register(ServerPlayConnectionEvents.Join { handler, _, _ ->
            CacheManager.addOrUpdatePlayer(handler.player.entityName, handler.player.getDeathCount())
        })
    }

    private fun ServerPlayerEntity.getDeathCount(): Int =
        statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))
}