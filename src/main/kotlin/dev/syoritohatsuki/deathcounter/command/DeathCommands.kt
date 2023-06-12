package dev.syoritohatsuki.deathcounter.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.syoritohatsuki.deathcounter.util.CacheManager
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TextContent
import net.minecraft.util.Formatting

fun CommandDispatcher<ServerCommandSource>.serverSideCommands() {
    listOf("dcs", "deathcounterserver").forEach { rootLiteral ->
        register(
            LiteralArgumentBuilder.literal<ServerCommandSource>(rootLiteral).then(
                LiteralArgumentBuilder.literal<ServerCommandSource>("top").executes { it.executeTopDeaths() }
            ).then(
                argument("page", IntegerArgumentType.integer(1)).executes { it.executeDeathPage() }
            ).then(
                argument("playerName", StringArgumentType.word()).executes { it.executePlayerDeaths() }
            ).then(
                argument("playerEntity", EntityArgumentType.player()).executes { it.executePlayerDeaths() }
            )
        )
    }
}

private fun CommandContext<ServerCommandSource>.executeTopDeaths(): Int {

    source.sendFeedback({
        MutableText.of(TextContent.EMPTY).apply {
            append(Text.translatable("message.top").styled { it.withColor(Formatting.YELLOW).withBold(true) })
            CacheManager.getTop().forEachIndexed { index, (name, count) ->
                append(Text.literal("\n${index + 1}. $name -> [$count]").styled {
                    when (index) {
                        0 -> it.withColor(0xFFD700)
                        1 -> it.withColor(0xC0C0C0)
                        2 -> it.withColor(0xCD7F32)
                        else -> it.withColor(Formatting.DARK_GRAY)
                    }
                })
            }
        }
    }, false)

    return Command.SINGLE_SUCCESS
}

private fun CommandContext<ServerCommandSource>.executeDeathPage(): Int {

    val page = IntegerArgumentType.getInteger(this, "page")

    source.sendFeedback({
        MutableText.of(TextContent.EMPTY).apply {
            CacheManager.getPage(page).let { list ->
                if (list.isEmpty()) append(Text.translatable("message.page.empty")) else {

                    append(Text.translatable("message.page", page).styled {
                        it.withColor(Formatting.YELLOW)
                            .withBold(true)
                    })

                    list.forEachIndexed { index, (name, count) ->
                        append(Text.literal("\n   "))
                        append(Text.translatable("message.other.die", name, count).styled {
                            if (index % 2 == 0) it.withColor(Formatting.RED)
                            else it.withColor(Formatting.DARK_RED)
                        })
                    }

                }
            }
        }
    }, false)

    return Command.SINGLE_SUCCESS
}

private fun CommandContext<ServerCommandSource>.executePlayerDeaths(): Int {

    val player: String = try {
        EntityArgumentType.getPlayer(this, "playerEntity").entityName
    } catch (e: Exception) {
        StringArgumentType.getString(this, "playerName")
    }

    source.sendFeedback({
        Text.translatable("message.other.die", player, CacheManager.getByPlayerName(player))
    }, false)

    return Command.SINGLE_SUCCESS
}