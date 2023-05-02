package dev.syoritohatsuki.deathcounter.client.toast

import com.mojang.blaze3d.systems.RenderSystem
import dev.syoritohatsuki.deathcounter.client.ClientConfigManager
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.toast.Toast
import net.minecraft.client.toast.Toast.TEXTURE
import net.minecraft.client.toast.Toast.TYPE
import net.minecraft.client.toast.ToastManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting

@Suppress("HttpUrlsUsage")
class WebToast(private var host: String, private var port: Int) : Toast {

    private var startTime: Long = 0

    private var justUpdated: Boolean = false

    companion object {
        fun show(manager: ToastManager, host: String, port: Int) {
            manager.getToast(WebToast::class.java, TYPE).apply {
                if (this == null) manager.add(WebToast(host, port))
                else setContent(host, port)
            }
        }
    }

    override fun draw(matrices: MatrixStack, manager: ToastManager, startTime: Long): Toast.Visibility {
        if (justUpdated) {
            this.startTime = startTime
            justUpdated = false
        }

        RenderSystem.setShaderTexture(0, TEXTURE)
        RenderSystem.polygonOffset(0.5f, 0.5f)
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, this.width, this.height)

        manager.client.textRenderer.draw(
            matrices, Text.translatable("toast.webui.stated").styled {
                it.withColor(Formatting.GREEN).withBold(true)
            }, 38.0f, 7.0f, 0
        )

        manager.client.textRenderer.draw(
            matrices, Text.literal("http://$host:$port").styled {
                it.withColor(Formatting.YELLOW)
            }, 35.0f, 18.0f, 0
        )

        return if (startTime - this.startTime < ClientConfigManager.read().showToastNotification.delay) Toast.Visibility.SHOW else Toast.Visibility.HIDE
    }

    fun setContent(host: String, port: Int) {
        this.host = host
        this.port = port
        this.justUpdated = true
    }
}