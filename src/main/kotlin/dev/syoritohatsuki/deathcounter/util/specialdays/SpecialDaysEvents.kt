package dev.syoritohatsuki.deathcounter.util.specialdays

import dev.syoritohatsuki.deathcounter.DeathCounter
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import java.time.LocalDate

object SpecialDaysEvents {

    private fun isDateInRange(from: LocalDate, to: LocalDate): Boolean = LocalDate.now() in from..to

    fun grand(playerEntity: ServerPlayerEntity) {
        SpecialDays.entries.forEach { day ->
            if (isDateInRange(day.from, day.to)) {
                playerEntity.server.advancementLoader[Identifier.of(DeathCounter.MOD_ID, day.path)]?.let {
                    val tracker = playerEntity.advancementTracker
                    val progress = tracker.getProgress(it)
                    progress.unobtainedCriteria.takeIf { !progress.isDone }?.forEach { criterion ->
                        tracker.grantCriterion(it, criterion)
                    }
                }
            }

        }
    }
}