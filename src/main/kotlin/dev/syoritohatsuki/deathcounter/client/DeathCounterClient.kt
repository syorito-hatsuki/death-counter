package dev.syoritohatsuki.deathcounter.client

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.client.command.warningCommand
import dev.syoritohatsuki.deathcounter.client.command.webUiStartCommand
import dev.syoritohatsuki.deathcounter.client.webui.WebClient.startWebClient
import dev.syoritohatsuki.deathcounter.client.webui.WebClient.stopWebClient
import dev.syoritohatsuki.deathcounter.network.DEATHS
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.text.*
import net.minecraft.util.Formatting
import org.slf4j.Logger

object DeathCounterClient : ClientModInitializer {

    private val clientLogger: Logger = LogUtils.getLogger()

    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { handler, _, client ->

            startWebClient(handler, client)

            client.player?.apply {
                if (ClientPlayNetworking.canSend(DEATHS)) {
                    ClientPlayNetworking.registerGlobalReceiver(DEATHS) { _, _, buf, _ ->
                        buf.readString().apply {
                            split(',').apply {
                                sendMessage(Text.of("You have died ${this[1]} times"))
                            }
                        }
                    }
                } else if (ClientConfigManager.read().showWarning) sendMessage(
                    MutableText
                        .of(TextContent.EMPTY)
                        .append(
                            Text.literal("\nDeath Counter not founded on server. Functionality is limited\n")
                                .styled { style ->
                                    style.withColor(Formatting.RED)
                                        .withBold(true)
                                        .withHoverEvent(
                                            HoverEvent(
                                                HoverEvent.Action.SHOW_TEXT,
                                                Text.literal("Without server-side mod you can't get other players death count :(")
                                                    .styled {
                                                        it.withColor(Formatting.RED).withBold(true)
                                                    }
                                            )
                                        )
                                }
                        )
                        .append(
                            Text.literal("For disable warning message, click me :3").styled {
                                it.withColor(Formatting.YELLOW)
                                it.withClickEvent(
                                    ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/dc warning false"
                                    )
                                )
                            }
                        )
                )
            }
        })

        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher, _ ->
            dispatcher.apply {
                warningCommand()
                webUiStartCommand()
            }
        })

        ClientPlayConnectionEvents.DISCONNECT.register(ClientPlayConnectionEvents.Disconnect { _, _ ->
            stopWebClient()
        })
    }
}