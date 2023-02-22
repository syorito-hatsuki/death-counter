package dev.syoritohatsuki.deathcounter.client.event

import com.mojang.brigadier.CommandDispatcher
import dev.syoritohatsuki.deathcounter.client.command.warningCommand
import dev.syoritohatsuki.deathcounter.client.command.webUiStartCommand
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource

fun CommandDispatcher<FabricClientCommandSource>.clientCommandEvent() {
    warningCommand()
    webUiStartCommand()
}