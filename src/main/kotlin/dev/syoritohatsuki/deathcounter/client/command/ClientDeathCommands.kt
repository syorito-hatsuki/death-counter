package dev.syoritohatsuki.deathcounter.client.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.BoolArgumentType.getBool
import com.mojang.brigadier.arguments.IntegerArgumentType.getInteger
import com.mojang.brigadier.arguments.IntegerArgumentType.integer
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
        register(literal<FabricClientCommandSource>(rootLiteral).executes { it.executeOwnDeaths() }.then(
            literal<FabricClientCommandSource>("warning").then(argument(
                "disable", bool()
            ).executes { it.executeWarning() })
        ).then(
            literal<FabricClientCommandSource>("webuinotify").then(argument(
                "disable", bool()
            ).executes { it.executeUiNotify() })
        ).then(literal<FabricClientCommandSource>("title").then(argument(
            "disable", bool()
        ).executes { it.executeTitle() }).then(argument("delayInTicks", integer(1)).executes { it.executeTitleDelay() })
        ).then(
            literal<FabricClientCommandSource>("chat").then(argument(
                "disable", bool()
            ).executes { it.executeChat() })
        )
        )
    }
}

private fun CommandContext<FabricClientCommandSource>.executeOwnDeaths(): Int {
    source.sendFeedback(
        Text.translatableWithFallback(
            "message.player.die", "You died %d times", source.player.getDeathCount()
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
        source.sendFeedback(when (this) {
            true -> Text.translatableWithFallback("message.notification.disabled", "Notify message disabled")
                .styled { it.withColor(Formatting.RED) }

            false -> Text.translatableWithFallback("message.notification.enabled", "Notify message enabled")
                .styled { it.withColor(Formatting.GREEN) }
        })
    }
    return Command.SINGLE_SUCCESS
}

private fun CommandContext<FabricClientCommandSource>.executeWarning(): Int {
    getBool(this, "disable").apply {
        ClientConfigManager.read().let {
            it.showWarning = this
            it.write()
        }
        source.sendFeedback(when (this) {
            true -> Text.translatableWithFallback("message.warning.enabled", "Warning message enabled")
                .styled { it.withColor(Formatting.GREEN) }

            false -> Text.translatableWithFallback("message.warning.disabled", "Warning message disabled")
                .styled { it.withColor(Formatting.RED) }
        })
    }
    return Command.SINGLE_SUCCESS
}

private fun CommandContext<FabricClientCommandSource>.executeTitle(): Int {
    getBool(this, "disable").apply {
        ClientConfigManager.read().let {
            it.titleMessage.disable = this
            it.write()
        }
        source.sendFeedback(when (this) {
            true -> Text.translatableWithFallback("message.title.disabled", "Title message disabled")
                .styled { it.withColor(Formatting.RED) }

            false -> Text.translatableWithFallback("message.title.enabled", "Title message enabled")
                .styled { it.withColor(Formatting.GREEN) }
        })
    }
    return Command.SINGLE_SUCCESS
}

private fun CommandContext<FabricClientCommandSource>.executeTitleDelay(): Int {
    getInteger(this, "delayInTicks").apply {
        ClientConfigManager.read().let {
            it.titleMessage.delayInTicks = this
            it.write()
        }
        source.sendFeedback(Text.translatableWithFallback("message.title.delay", "Changed delay to %d ticks")
            .styled { it.withColor(Formatting.GREEN) })
    }
    return Command.SINGLE_SUCCESS
}

private fun CommandContext<FabricClientCommandSource>.executeChat(): Int {
    getBool(this, "disable").apply {
        ClientConfigManager.read().let {
            it.chatMessage.disable = this
            it.write()
        }
        source.sendFeedback(when (this) {
            true -> Text.translatableWithFallback("message.chat.disabled", "Chat message disabled")
                .styled { it.withColor(Formatting.RED) }

            false -> Text.translatableWithFallback("message.chat.enabled", "Chat message enabled")
                .styled { it.withColor(Formatting.GREEN) }
        })
    }
    return Command.SINGLE_SUCCESS
}