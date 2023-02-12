package dev.syoritohatsuki.deathcounter.legacy

import dev.syoritohatsuki.deathcounter.legacy.server.command.DeathListCommand.registerDeathCommands
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object DeathCounter : ModInitializer {
    override fun onInitialize() {

        DuckyUpdater.checkForUpdate("7x0zk3YH", "deathcounter")

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            dispatcher.registerDeathCommands()
        })
    }
}