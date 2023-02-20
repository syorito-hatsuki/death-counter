package dev.syoritohatsuki.deathcounter.client

import dev.syoritohatsuki.deathcounter.DeathCounter.MOD_ID
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

object ClientConfigManager {
    private val clientConfigDir: File = Paths.get("", "config", MOD_ID, "client").toFile()
    private val clientConfigFile = File(clientConfigDir, "config.json")

    private val clientConfigJson = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    init {
        if (!clientConfigDir.exists()) clientConfigDir.mkdirs()
        clientConfigFile.writeText(clientConfigJson.encodeToString(ClientConfig()))
    }

    fun read() = clientConfigJson.decodeFromString<ClientConfig>(clientConfigFile.readText())

    fun ClientConfig.write() = clientConfigFile.writeText(clientConfigJson.encodeToString(this))

    @Serializable
    data class ClientConfig(
        val showWarning: Boolean = true,
        val showWebUiMessage: Boolean = true
    )
}