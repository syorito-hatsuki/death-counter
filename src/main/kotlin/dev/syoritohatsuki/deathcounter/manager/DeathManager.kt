package dev.syoritohatsuki.deathcounter.manager

import dev.syoritohatsuki.deathcounter.DeathCounter.json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File
import java.nio.file.Paths

object DeathManager {
    private val configDir: File = Paths.get("", "config", "deathcounter").toFile()
    private val deathsFile = File(configDir, "deaths.json")

    init {
        if (!deathsFile.exists()) {
            if (!configDir.exists()) configDir.mkdirs()
            deathsFile.apply {
                createNewFile()
                writeText(json.encodeToString(HashMap<String, Int>()))
            }
        } else deathsFile.writeText(json.encodeToString(deaths()))
    }

    fun deaths(): HashMap<String, Int> = json.decodeFromString(deathsFile.readText())

    fun getPlayerDeathCount(username: String): Int = deaths()[username] ?: 0

    fun writeDeath(username: String, deathStat: Int) {
        val death = deaths()
        death[username] = deathStat
        deathsFile.writeText(json.encodeToString(death))
    }

    fun getDeathListByPage(page: Int): Map<String, Int> {
        val list = deaths().toList().sortedByDescending { it.second }
        val range = (9 * page)

        return mutableMapOf<String, Int>().apply {
            for (index in range - 9 until range) {
                if (index < list.size) {
                    this[list[index].first] = list[index].second
                }
            }
        }
    }
}