package dev.syoritohatsuki.deathcounter.client

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.network.DEATHS
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.text.HoverEvent
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import org.slf4j.Logger

object DeathCounterClient : ClientModInitializer {

    private val clientLogger: Logger = LogUtils.getLogger()

    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { _, _, client ->
            client.player?.apply {
                if (ClientPlayNetworking.canSend(DEATHS)) {
                    ClientPlayNetworking.registerGlobalReceiver(DEATHS) { _, _, buf, _ ->
                        buf.readString().apply {
                            clientLogger.info("Data from server: $this")
                            split(',').apply {
                                sendMessage(Text.of("Player: ${this[0]} | Count: ${this[1]}"))
                            }
                        }
                    }
                } else sendMessage(
                    Text.literal("\nDeath Counter not founded on server. Functionality is limited\n").styled { style ->
                        style.withColor(Formatting.RED)
                            .withBold(true)
                            .withHoverEvent(
                                HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    Text.literal("Without server-side mod you can't see other players death count :(")
                                        .styled {
                                            it.withColor(Formatting.RED).withBold(true)
                                        }
                                )
                            )
                    }
                )
            }
        })
    }
}