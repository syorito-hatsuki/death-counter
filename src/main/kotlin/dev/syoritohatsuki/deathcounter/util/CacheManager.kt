package dev.syoritohatsuki.deathcounter.util

import dev.syoritohatsuki.deathcounter.DeathCounter.MOD_ID
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

object CacheManager {

    private val cacheDir = Paths.get("", "cache", MOD_ID).toFile()
    private val cacheFile = File(cacheDir, "deaths.json")

    private val cacheJson = Json { ignoreUnknownKeys = true; prettyPrint = true }

    private var deathMap: MutableMap<String, Int> = mutableMapOf()

    init {
        if (!cacheDir.exists()) cacheDir.mkdirs()
        if (!cacheFile.exists()) cacheFile.writeText(cacheJson.encodeToString(deathMap))
        deathMap = cacheJson.decodeFromString(cacheFile.readText())
    }

    fun getByPlayerName(name: String): Int = deathMap[name] ?: 0

    fun getPage(page: Int): List<Pair<String, Int>> = mutableListOf<Pair<String, Int>>().apply {
        deathMap.toList().sortedBy { it.first }.let {
            for (index in (9 * page) - 9 until (9 * page)) {
                if (index < it.size) add(Pair(it[index].first, it[index].second))
            }
        }
    }

    fun getTop(): List<Pair<String, Int>> = mutableListOf<Pair<String, Int>>().apply {
        var index = 0
        deathMap.toList().sortedByDescending { it.second }.forEach {
            if (index == 5) return@forEach
            add(Pair(it.first, it.second))
            index++
        }
    }

    fun addOrUpdatePlayer(player: String, deathCount: Int) {
        deathMap[player] = deathCount
        cacheFile.writeText(cacheJson.encodeToString(deathMap))
    }
}