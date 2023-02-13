package dev.syoritohatsuki.deathcounter.client

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.network.DEATHS
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.text.Text
import org.slf4j.Logger

object DeathCounterClient : ClientModInitializer {

    val clientLogger: Logger = LogUtils.getLogger()

    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { _, _, _ ->
            if (ClientPlayNetworking.canSend(DEATHS)) {
                clientLogger.info("DC available")
                ClientPlayNetworking.registerGlobalReceiver(DEATHS) { client, _, buf, _ ->
                    buf.readString().apply {
                        clientLogger.info("Data from server: $this")
                        split(',').apply {
                            client.player?.sendMessage(Text.of("Player: ${this[0]} | Count: ${this[1]}"))
                        }
                    }
                }
            } else clientLogger.info("DC not available")
        })
    }
}