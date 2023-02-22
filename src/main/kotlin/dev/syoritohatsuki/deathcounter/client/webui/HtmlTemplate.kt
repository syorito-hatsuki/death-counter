package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import kotlinx.html.*

@Suppress("HttpUrlsUsage")
fun HTML.htmlTemplate(player: String) {
    head {
        title("Death Counter")
        script(type = ScriptType.textJavaScript) {
            unsafe {
                raw(
                    """
                    setInterval(async function () {
                        try {
                            const response = await fetch("http://${
                        ClientConfigManager.read().webSetup.localAddress
                    }:${
                        ClientConfigManager.read().webSetup.servicePort
                    }/${player}")
                            if (response.ok) {
                                document.getElementById("death").textContent = await response.json();
                            } else {
                                document.getElementById("death").textContent = response.status.toString()
                            }
                        } catch (e) {
                            document.getElementById("death").textContent = e
                        }
                    }, ${ClientConfigManager.read().webSetup.refreshDelayMs});
                """
                )
            }
        }
    }
    body {
        p {
            id = "death"
        }
    }
}