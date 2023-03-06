package dev.syoritohatsuki.deathcounter.client.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.BoolArgumentType.getBool
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import dev.syoritohatsuki.deathcounter.client.ClientConfigManager.write
import dev.syoritohatsuki.deathcounter.client.extension.getDeathCount
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting

fun clientSideCommands(rootLiteral: String): LiteralArgumentBuilder<FabricClientCommandSource> {
    return literal<FabricClientCommandSource>(rootLiteral).executes { it.executeOwnDeaths() }.then(
        literal<FabricClientCommandSource>("warning").then(
            ClientCommandManager.argument("disable", bool()).executes { it.executeWarning() }
        )
    ).then(
        literal<FabricClientCommandSource?>("webuinotify").then(
            ClientCommandManager.argument("disable", bool()).executes { it.executeUiNotify() }
        )
    )
}

private fun CommandContext<FabricClientCommandSource>.executeOwnDeaths(): Int {
    source.sendFeedback(TranslatableText("message.player.die", source.player.getDeathCount()))
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
                true -> TranslatableText("message.notification.enabled").styled { it.withColor(Formatting.GREEN) }
                false -> TranslatableText("message.notification.disabled").styled { it.withColor(Formatting.RED) }
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
                true -> TranslatableText("message.warning.enabled").styled { it.withColor(Formatting.GREEN) }
                false -> TranslatableText("message.warning.disabled").styled { it.withColor(Formatting.RED) }
            }
        )
    }
    return Command.SINGLE_SUCCESS
}