package dev.syoritohatsuki.deathcounter.util.specialdays

import java.time.LocalDate

enum class SpecialDays(val from: LocalDate, val to: LocalDate, val path: String) {
    HALLOWEEN(LocalDate.of(LocalDate.now().year, 10, 31), LocalDate.of(LocalDate.now().year, 11, 1), "events/halloween")
}