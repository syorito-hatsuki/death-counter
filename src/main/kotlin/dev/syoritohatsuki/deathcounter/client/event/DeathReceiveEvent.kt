package dev.syoritohatsuki.deathcounter.client.event

import net.minecraft.client.MinecraftClient
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.Text


fun PacketByteBuf.receiveDeath(client: MinecraftClient) {
    readString().apply {
        split(',').apply {
            if (client.player?.entityName == this[0]) client.player?.sendMessage(Text.of("You have died ${this[1]} times"))
            /*   TODO Write data to cache file   */
        }
    }
}