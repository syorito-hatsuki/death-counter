package dev.syoritohatsuki.deathcounter.legacy.client.manager

import dev.syoritohatsuki.deathcounter.legacy.client.dto.ClientConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

object ClientConfigManager {

    private val configDir: File = Paths.get("", "config", "deathcounter", "client").toFile()
    private val configFile = File(configDir, "config.json")
    val json = Json { ignoreUnknownKeys = true; encodeDefaults = true; prettyPrint = true }

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