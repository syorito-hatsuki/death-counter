package dev.syoritohatsuki.deathcounter.client.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.BoolArgumentType.getBool
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import dev.syoritohatsuki.deathcounter.client.ClientConfigManager.write
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text

fun CommandDispatcher<FabricClientCommandSource>.warningCommand() {
    register(
        literal<FabricClientCommandSource>("dc").then(
            literal<FabricClientCommandSource>("warning").then(
                argument("disable", bool()).executes {
                    getBool(it, "disable").apply {
                        ClientConfigManager.read().copy(showWarning = this).write()
                        it.source.sendFeedback(
                            Text.literal(
                                when (this) {
                                    true -> "Warning message enabled"
                                    false -> "Warning message disabled"
                                }
                            )
                        )
                    }
                    Command.SINGLE_SUCCESS
                }
            )
        )
    )
}

fun CommandDispatcher<FabricClientCommandSource>.webUiStartCommand() {
    register(
        literal<FabricClientCommandSource>("dc").then(
            literal<FabricClientCommandSource?>("webuinotify").then(
                argument("disable", bool()).executes {
                    getBool(it, "disable").apply {
                        ClientConfigManager.read().copy(showWebUiMessage = this).write()
                        it.source.sendFeedback(
                            Text.literal(
                                when (this) {
                                    true -> "Notify message enabled"
                                    false -> "Notify message disabled"
                                }
                            )
                        )
                    }
                    Command.SINGLE_SUCCESS
                }
            )
        )
    )
}