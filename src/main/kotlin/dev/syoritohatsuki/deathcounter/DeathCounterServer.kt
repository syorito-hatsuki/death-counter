package dev.syoritohatsuki.deathcounter

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.deathcounter.util.serverModule
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import org.slf4j.Logger
import java.util.concurrent.TimeUnit

object DeathCounterServer : DedicatedServerModInitializer {

    val serverLogger: Logger = LogUtils.getLogger()

    private lateinit var webServer: ApplicationEngine
    override fun onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(ServerLifecycleEvents.ServerStarted {
            webServer = embeddedServer(
                CIO,
                port = 1540,
                host = it.serverIp.ip(),
                module = Application::serverModule
            ).start()
        })

        ServerLifecycleEvents.SERVER_STOPPING.register(ServerLifecycleEvents.ServerStopping {
            webServer.stop(1, 5, TimeUnit.SECONDS)
            serverLogger.info("${javaClass.simpleName} WebServer stopped")
        })
    }

    private fun String?.ip(): String = when {
        isNullOrBlank() -> "0.0.0.0"
        else -> this
    }
}