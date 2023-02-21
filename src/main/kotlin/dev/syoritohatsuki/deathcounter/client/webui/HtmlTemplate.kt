package dev.syoritohatsuki.deathcounter.client.webui

import kotlinx.html.*

fun HTML.htmlTemplate(remoteIp: String, player: String, delay: Int) {
    head {
        title("Death Counter")
        script(type = ScriptType.textJavaScript) {
            unsafe {
                raw(
                    """
                        setInterval(async function () {
                            try {
                                const response = await fetch("http://${remoteIp}:1540/${player}")
                                if (response.ok) {
                                    document.getElementById("death").textContent = await response.json();
                                } else {
                                    document.getElementById("death").textContent = response.status.toString()
                                }
                            } catch (e) {
                                document.getElementById("death").textContent = e
                            }
                        }, ${delay});
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