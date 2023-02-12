package dev.syoritohatsuki.deathcounter.legacy.server.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.syoritohatsuki.deathcounter.legacy.manager.DeathManager
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object DeathListCommand {
    fun CommandDispatcher<ServerCommandSource>.registerDeathCommands() {
        register(
            CommandManager.literal("deathcounter").executes { it.executeOwnerDeaths() }
                .then(CommandManager.argument("page", IntegerArgumentType.integer(1))
                    .executes { it.executeAllDeaths() })
                .then(CommandManager.argument("playerWord", StringArgumentType.word())
                    .executes { it.executePlayerDeaths() })
                .then(CommandManager.argument("playerEntity", EntityArgumentType.player())
                    .executes { it.executePlayerDeaths() })
        )
    }

    private fun CommandContext<ServerCommandSource>.executeOwnerDeaths(): Int {
        source.sendFeedback(
            Text.literal("${source.playerOrThrow.entityName} died ${DeathManager.getPlayerDeathCount(source.playerOrThrow.entityName)} times"),
            false
        )
        return Command.SINGLE_SUCCESS
    }

    private fun CommandContext<ServerCommandSource>.executePlayerDeaths(): Int {

        val player: String = try {
            EntityArgumentType.getPlayer(this, "playerEntity").entityName
        } catch (e: Exception) {
            StringArgumentType.getString(this, "playerWord")
        }

        source.sendFeedback(
            Text.literal("§b$player died ${DeathManager.getPlayerDeathCount(player)} times"), false
        )

        return Command.SINGLE_SUCCESS
    }

    private fun CommandContext<ServerCommandSource>.executeAllDeaths(): Int {

        val page = IntegerArgumentType.getInteger(this, "page")

        DeathManager.getDeathListByPage(page).apply {
            if (isEmpty()) {
                source.sendFeedback(Text.of("Page is empty"), false)
                return Command.SINGLE_SUCCESS
            }

            source.sendFeedback(Text.of("§e§lDeathCounter Page [$page]"), false)

            var index = 0

            forEach { (name, count) ->
                if (index % 2 == 0) source.sendFeedback(
                    Text.of("§4    $name died $count times"),
                    false
                ) else source.sendFeedback(Text.of("§c    $name died $count times"), false)
                index++
            }
        }
        return Command.SINGLE_SUCCESS
    }
}