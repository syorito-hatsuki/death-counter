package dev.syoritohatsuki.deathcounter

import dev.syoritohatsuki.deathcounter.command.serverSideCommands
import dev.syoritohatsuki.deathcounter.event.PlayerDeathEvents
import dev.syoritohatsuki.deathcounter.network.DeathPacket
import dev.syoritohatsuki.deathcounter.util.CacheManager
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.packet.CustomPayload
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.stat.Stats

object DeathCounter : ModInitializer {

    const val MOD_ID = "deathcounter"

    override fun onInitialize() {

        PayloadTypeRegistry.playC2S().register(DeathPacket.id, DeathPacket.PACKET_CODEC)

        ServerPlayNetworking.registerGlobalReceiver(DeathPacket.id) { _: CustomPayload, _ -> }

        PlayerDeathEvents.DEATH.register(PlayerDeathEvents.Death { player ->
            CacheManager.addOrUpdatePlayer(player.name.literalString ?: return@Death, player.getDeathCount())
        })

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            dispatcher.serverSideCommands()
        })

        ServerPlayConnectionEvents.JOIN.register(ServerPlayConnectionEvents.Join { handler, _, _ ->
            CacheManager.addOrUpdatePlayer(
                handler.player.name.literalString ?: return@Join, handler.player.getDeathCount()
            )
        })
    }

    private fun ServerPlayerEntity.getDeathCount(): Int =
        statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))
}