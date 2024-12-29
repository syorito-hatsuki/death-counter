package dev.syoritohatsuki.deathcounter.client

import dev.syoritohatsuki.deathcounter.DeathCounter.MOD_ID
import kotlinx.serialization.Serializable
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
        if (!clientConfigFile.exists()) clientConfigFile.writeText(clientConfigJson.encodeToString(ClientConfig()))
    }

    fun read() = clientConfigJson.decodeFromString<ClientConfig>(clientConfigFile.readText())

    fun ClientConfig.write() = clientConfigFile.writeText(clientConfigJson.encodeToString(this))

    @Serializable
    data class ClientConfig(
        val titleMessage: TitleMessage = TitleMessage(),
        val chatMessage: ChatMessage = ChatMessage(),
        var showWarning: Boolean = true,
        val showToastNotification: ToastNotification = ToastNotification(),
        val webSetup: WebSetup = WebSetup(),
    ) {
        @Serializable
        data class WebSetup(
            val localAddress: String = "0.0.0.0",
            val servicePort: Int = 1540,
            val refreshDelayMs: Int = 5000,
        )

        @Serializable
        data class ToastNotification(
            var disable: Boolean = false,
            val delay: Int = 5000,
        )

        @Serializable
        data class TitleMessage(
            var disable: Boolean = false,
            var delayInTicks: Int = 60,
        )

        @Serializable
        data class ChatMessage(
            var disable: Boolean = false,
        )
    }
}