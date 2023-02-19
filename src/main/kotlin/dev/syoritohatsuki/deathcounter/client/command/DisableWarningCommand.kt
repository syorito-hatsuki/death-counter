package dev.syoritohatsuki.deathcounter.client.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.BoolArgumentType.getBool
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text

fun CommandDispatcher<FabricClientCommandSource>.disableWarningCommand() {
    register(
        literal<FabricClientCommandSource?>("dc").then(
            literal<FabricClientCommandSource?>("warning").then(
                argument("disable", bool()).executes {
                    it.source.sendFeedback(Text.of(getBool(it, "disable").toString() + "\nMessage disabled"))
                    Command.SINGLE_SUCCESS
                }
            )
        )
    )
}