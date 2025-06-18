package dev.syoritohatsuki.deathcounter.client.toast

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.toast.Toast
import net.minecraft.client.toast.ToastManager
import net.minecraft.text.Text
import net.minecraft.util.Colors
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

class WebToast(private var host: String, private var port: Int) : Toast {

    private val maxTime = ClientConfigManager.read().showToastNotification.delay
    private var visibility = Toast.Visibility.SHOW

    override fun getVisibility(): Toast.Visibility = visibility

    override fun update(manager: ToastManager, time: Long) {
        if (time > maxTime) visibility = Toast.Visibility.HIDE
    }

    override fun draw(context: DrawContext, textRenderer: TextRenderer, startTime: Long) {
        context.drawGuiTexture(
            RenderPipelines.GUI_TEXTURED,
            Identifier.of("toast/advancement"),
            0,
            0,
            this.width,
            this.height
        )

        context.drawText(
            textRenderer,
            Text.translatableWithFallback("toast.webui.stated", "WebUI Started").styled {
                it.withColor(Formatting.GREEN).withBold(true)
            },
            38,
            7,
            Colors.WHITE,
            false
        )

        @Suppress("HttpUrlsUsage")
        context.drawText(
            textRenderer,
            Text.literal("http://$host:$port").styled {
                it.withColor(Formatting.YELLOW)
            },
            35,
            18,
            Colors.WHITE,
            false
        )
    }
}