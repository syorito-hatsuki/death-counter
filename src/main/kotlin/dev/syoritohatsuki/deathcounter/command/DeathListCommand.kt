package dev.syoritohatsuki.deathcounter.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
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
                .then(CommandManager.argument("page", IntegerArgumentType.integer(1))
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
            Text.literal("§b$player died ${DeathManager.getPlayerDeathCount(player)} times"), false
        )

        return Command.SINGLE_SUCCESS
    }

    private fun executeAllDeaths(context: CommandContext<ServerCommandSource>): Int {

        val page = IntegerArgumentType.getInteger(context, "page")

        DeathManager.getDeathListByPage(page).apply {
            if (isEmpty()) {
                context.source.sendFeedback(Text.of("Page is empty"), false)
                return Command.SINGLE_SUCCESS
            }

            context.source.sendFeedback(Text.of("§e§lDeathCounter Page [$page]"), false)

            var index = 0

            forEach { (name, count) ->
                if (index % 2 == 0) context.source.sendFeedback(
                    Text.of("§4    $name died $count times"),
                    false
                ) else context.source.sendFeedback(Text.of("§c    $name died $count times"), false)
                index++
            }
        }
        return Command.SINGLE_SUCCESS
    }
}