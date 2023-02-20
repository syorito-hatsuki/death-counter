package dev.syoritohatsuki.deathcounter.client

import dev.syoritohatsuki.deathcounter.legacy.client.dto.ClientConfig
import dev.syoritohatsuki.deathcounter.legacy.client.manager.ClientConfigManager
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

object ClientConfig {
    private val clientConfigDir: File = Paths.get("", "config", "deathcounter", "client").toFile()
    private val clientConfigFile = File(clientConfigDir, "config.json")

    private val clientConfigJson = Json {
        prettyPrint= true
        encodeDefaults = true
    }

    init {
        if (!clientConfigFile.exists()) {
            if (!clientConfigDir.exists()) clientConfigDir.mkdirs()
            clientConfigFile.apply {
                createNewFile()
                writeText(ClientConfigManager.json.encodeToString(dev.syoritohatsuki.deathcounter.legacy.client.dto.ClientConfig()))
            }
        } else clientConfigFile.writeText(ClientConfigManager.json.encodeToString(read()))
    }

    fun read(): ClientConfig = clientConfigJson.decodeFromString(clientConfigFile.readText())

    fun write(
        showWarning: Boolean? = null,
        showWebUiMessage: Boolean? = null
    ) {
        clientConfigFile.writeText(clientConfigJson.encodeToString(read().copy(
            showWarning = showWarning,
            showWebUiMessage = showWebUiMessage
        )))
    }

    data class ClientConfig(
        val showWarning: Boolean = true,
        val showWebUiMessage: Boolean = true
    )
}