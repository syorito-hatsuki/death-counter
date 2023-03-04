package dev.syoritohatsuki.deathcounter.client.extension

import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.stat.Stats

fun ClientPlayerEntity.getDeathCount(): Int = statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))