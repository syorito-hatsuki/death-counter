package dev.syoritohatsuki.deathcounter.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity

object PlayerDeathCallback {
    var DEATH: Event<PlayerDeath> = EventFactory.createArrayBacked(
        PlayerDeath::class.java
    ) { callbacks: Array<PlayerDeath> ->
        object : PlayerDeath {
            override fun onDie(player: ServerPlayerEntity) {
                callbacks.forEach {
                    it.onDie(player)
                }
            }
        }
    }

    interface PlayerDeath {
        fun onDie(player: ServerPlayerEntity)
    }
}