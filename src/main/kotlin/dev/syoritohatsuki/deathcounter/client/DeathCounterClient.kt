package dev.syoritohatsuki.deathcounter.client

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.client.event.clientCommandEvent
import dev.syoritohatsuki.deathcounter.client.event.receiveDeath
import dev.syoritohatsuki.deathcounter.client.extension.message.modUnavailableOnServerMessage
import dev.syoritohatsuki.deathcounter.client.webui.WebClient.startWebClient
import dev.syoritohatsuki.deathcounter.client.webui.WebClient.stopWebClient
import dev.syoritohatsuki.deathcounter.network.DEATHS
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object DeathCounterClient : ClientModInitializer {

    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { _, _, client ->
            startWebClient(client)

            if (!ClientPlayNetworking.canSend(ON_DEATH) && ClientConfigManager.read().showWarning)
                client.player?.modUnavailableOnServerMessage()
        })

        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher, _ ->
            dispatcher.clientCommandEvent()
        })

        ClientPlayConnectionEvents.DISCONNECT.register(ClientPlayConnectionEvents.Disconnect { _, _ ->
            stopWebClient()
        })
    }
}