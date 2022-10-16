package dev.syoritohatsuki.deathcounter.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.syoritohatsuki.deathcounter.manager.DeathManager
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object DeathListCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            CommandManager.literal("deathcounter").executes { executeOwnerDeaths(it) }
                .then(CommandManager.literal("all")
                    .executes { executeAllDeaths(it) })
                .then(CommandManager.argument("playerWord", StringArgumentType.word())
                    .executes { executePlayerDeaths(it) })
                .then(CommandManager.argument("playerEntity", EntityArgumentType.player())
                    .executes { executePlayerDeaths(it) })
        )
    }

    private fun executeOwnerDeaths(context: CommandContext<ServerCommandSource>): Int {
        context.source.sendFeedback(
            Text.literal("${context.source.playerOrThrow.entityName} died ${DeathManager.getPlayerDeathCount(context.source.playerOrThrow.entityName)} times"),
            false
        )
        return Command.SINGLE_SUCCESS
    }

    private fun executePlayerDeaths(context: CommandContext<ServerCommandSource>): Int {

        val player: String = try {
            EntityArgumentType.getPlayer(context, "playerEntity").entityName
        } catch (e: Exception) {
            StringArgumentType.getString(context, "playerWord")
        }

        context.source.sendFeedback(
            Text.literal(
                "$player died ${
                    DeathManager.getPlayerDeathCount(player)
                } times"
            ),
            false
        )

        return Command.SINGLE_SUCCESS
    }

    private fun executeAllDeaths(context: CommandContext<ServerCommandSource>): Int {
        context.source.sendFeedback(
            Text.literal(
                StringBuilder().apply {
                    append("=====[ DeathCounter List ]=====")
                    DeathManager.deaths().forEach { (name, count) ->
                        append("\n$name died $count times")
                    }
                    append("\n===============================")
                }.toString()
            ), false
        )
        return Command.SINGLE_SUCCESS
    }
}