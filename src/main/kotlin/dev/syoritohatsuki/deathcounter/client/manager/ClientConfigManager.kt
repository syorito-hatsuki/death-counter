package dev.syoritohatsuki.deathcounter.client.manager

import dev.syoritohatsuki.deathcounter.DeathCounter.json
import dev.syoritohatsuki.deathcounter.client.dto.ClientConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File
import java.nio.file.Paths

object ClientConfigManager {
    private val configDir: File = Paths.get("", "config", "deathcounter", "client").toFile()
    private val configFile = File(configDir, "config.json")

    init {
        if (!configFile.exists()) {
            if (!configDir.exists()) configDir.mkdirs()
            configFile.apply {
                createNewFile()
                writeText(json.encodeToString(ClientConfig()))
            }
        } else configFile.writeText(json.encodeToString(read()))
    }

    fun read(): ClientConfig = json.decodeFromString(configFile.readText())
}