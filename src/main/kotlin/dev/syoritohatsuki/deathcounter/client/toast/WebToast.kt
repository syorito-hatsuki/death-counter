package dev.syoritohatsuki.deathcounter.client.toast

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.toast.Toast
import net.minecraft.client.toast.ToastManager
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

@Suppress("HttpUrlsUsage")
class WebToast(private var host: String, private var port: Int) : Toast {

    private var startTime: Long = 0

    private var justUpdated: Boolean = false

    override fun draw(context: DrawContext, manager: ToastManager, startTime: Long): Toast.Visibility {
        if (justUpdated) {
            this.startTime = startTime
            justUpdated = false
        }

        context.drawTexture(Identifier("textures/gui/toasts.png"), 0, 0, 0, 0, this.width, this.height)

        context.drawText(
            manager.client.textRenderer, Text.translatableWithFallback("toast.webui.stated", "WebUI Started").styled {
                it.withColor(Formatting.GREEN).withBold(true)
            }, 38, 7, 0, false
        )

        context.drawText(manager.client.textRenderer, Text.literal("http://$host:$port").styled {
            it.withColor(Formatting.YELLOW)
        }, 35, 18, 0, false)

        return if (startTime - this.startTime < ClientConfigManager.read().showToastNotification.delay) Toast.Visibility.SHOW else Toast.Visibility.HIDE
    }
}