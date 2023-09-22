package dev.syoritohatsuki.deathcounter.client.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.BoolArgumentType.getBool
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import dev.syoritohatsuki.deathcounter.client.ClientConfigManager.write
import dev.syoritohatsuki.deathcounter.client.extension.getDeathCount
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Formatting

fun CommandDispatcher<FabricClientCommandSource>.clientSideCommands() {
    listOf("dcc", "deathcounterclient").forEach { rootLiteral ->
        register(
            literal<FabricClientCommandSource>(rootLiteral).executes { it.executeOwnDeaths() }.then(
                literal<FabricClientCommandSource>("warning").then(
                    argument("disable", bool()).executes { it.executeWarning() }
                )
            ).then(
                literal<FabricClientCommandSource?>("webuinotify").then(
                    argument("disable", bool()).executes { it.executeUiNotify() }
                )
            )
        )
    }
}

private fun CommandContext<FabricClientCommandSource>.executeOwnDeaths(): Int {
    source.sendFeedback(
        Text.translatableWithFallback(
            "message.player.die",
            "You died %d times",
            source.player.getDeathCount()
        )
    )
    return Command.SINGLE_SUCCESS
}

private fun CommandContext<FabricClientCommandSource>.executeUiNotify(): Int {
    getBool(this, "disable").apply {
        ClientConfigManager.read().let {
            it.showToastNotification.disable = this
            it.write()
        }
        source.sendFeedback(
            when (this) {
                true -> Text.translatableWithFallback("message.notification.enabled", "Notify message enabled")
                    .styled { it.withColor(Formatting.GREEN) }

                false -> Text.translatableWithFallback("message.notification.disabled", "Notify message disabled")
                    .styled { it.withColor(Formatting.RED) }
            }
        )
    }
    return Command.SINGLE_SUCCESS
}

private fun CommandContext<FabricClientCommandSource>.executeWarning(): Int {
    getBool(this, "disable").apply {
        ClientConfigManager.read().let {
            it.showWarning = this
            it.write()
        }
        source.sendFeedback(
            when (this) {
                true -> Text.translatableWithFallback("message.warning.enabled", "Warning message enabled")
                    .styled { it.withColor(Formatting.GREEN) }

                false -> Text.translatableWithFallback("message.warning.disabled", "Warning message disabled")
                    .styled { it.withColor(Formatting.RED) }
            }
        )
    }
    return Command.SINGLE_SUCCESS
}