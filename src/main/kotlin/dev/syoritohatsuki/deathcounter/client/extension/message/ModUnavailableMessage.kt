package dev.syoritohatsuki.deathcounter.client.extension.message

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.ClickEvent
import net.minecraft.text.HoverEvent
import net.minecraft.text.Text
import net.minecraft.util.Formatting

fun PlayerEntity.modUnavailableOnServerMessage() {
    sendMessage(
        Text.translatable("message.warning")
            .styled { style ->
                style.withColor(Formatting.RED)
                    .withBold(true)
                    .withHoverEvent(
                        HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            Text.translatable("message.warning.tooltip")
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