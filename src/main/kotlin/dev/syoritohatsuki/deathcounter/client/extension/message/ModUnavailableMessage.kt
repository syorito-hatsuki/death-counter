package dev.syoritohatsuki.deathcounter.client.extension.message

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.ClickEvent
import net.minecraft.text.HoverEvent
import net.minecraft.text.Text
import net.minecraft.util.Formatting

fun PlayerEntity.modUnavailableOnServerMessage() {
    sendMessage(
        Text.literal("\nDeath Counter not founded on server. Functionality is limited\nFor disable warning message, click on it\n")
            .styled { style ->
                style.withColor(Formatting.RED)
                    .withBold(true)
                    .withHoverEvent(
                        HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            Text.literal("Without server-side mod you can't get other players death count :(")
                                .styled { subStyle ->
                                    subStyle.withColor(Formatting.RED)
                                        .withBold(true)
                                }
                        )
                    ).withClickEvent(
                        ClickEvent(
                            ClickEvent.Action.RUN_COMMAND,
                            "/dc warning false"
                        )
                    )
            }
    )
}