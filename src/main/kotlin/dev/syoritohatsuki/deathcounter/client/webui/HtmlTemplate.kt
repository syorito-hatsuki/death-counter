package dev.syoritohatsuki.deathcounter.client.webui

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import kotlinx.html.*

@Suppress("HttpUrlsUsage")
fun HTML.htmlTemplate(host: String, port: Int) {
    head {
        title("Death Counter")
        script(type = ScriptType.textJavaScript) {
            unsafe {
                raw(
                    """
                        setInterval(async function () {
                        try {
                            const response = await fetch("http://$host:$port/deaths")
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