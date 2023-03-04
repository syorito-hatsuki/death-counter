package dev.syoritohatsuki.deathcounter.event

import dev.syoritohatsuki.deathcounter.event.PlayerDeathEvents.Death
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity

object PlayerDeathEvents {
    val DEATH: Event<Death> = EventFactory.createArrayBacked(Death::class.java) { callbacks: Array<Death> ->
        Death { player ->
            callbacks.forEach {
                it.onDie(player)
            }
        }
    }

    fun interface Death {
        fun onDie(player: ServerPlayerEntity)
    }
}