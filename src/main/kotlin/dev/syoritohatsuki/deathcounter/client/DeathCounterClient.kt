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
import org.slf4j.Logger

object DeathCounterClient : ClientModInitializer {

    private val clientLogger: Logger = LogUtils.getLogger()

    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { _, _, client ->
            ClientPlayNetworking.canSend(DEATHS).apply {
                startWebClient(client)
                if (this) ClientPlayNetworking.registerGlobalReceiver(DEATHS) { _, _, buf, _ ->
                    buf.receiveDeath(client)
                } else if (ClientConfigManager.read().showWarning) client.player?.modUnavailableOnServerMessage()
            }
        })

        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher, _ ->
            dispatcher.clientCommandEvent()
        })

        ClientPlayConnectionEvents.DISCONNECT.register(ClientPlayConnectionEvents.Disconnect { _, _ ->
            stopWebClient()
        })
    }
}