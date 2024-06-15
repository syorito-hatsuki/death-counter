package dev.syoritohatsuki.deathcounter.network

import dev.syoritohatsuki.deathcounter.DeathCounter.MOD_ID
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

object DeathPacket : CustomPayload {
    private val PACKET_ID: CustomPayload.Id<DeathPacket> = CustomPayload.Id(Identifier.of(MOD_ID, "on_death"))
    val PACKET_CODEC: PacketCodec<ByteBuf, DeathPacket> = PacketCodec.unit(DeathPacket)

    override fun getId(): CustomPayload.Id<DeathPacket> = PACKET_ID
}