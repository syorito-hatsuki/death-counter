package dev.syoritohatsuki.template

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.template.registry.BlocksRegistry
import dev.syoritohatsuki.template.registry.EnchantmentsRegistry
import dev.syoritohatsuki.template.registry.ItemsRegistry
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger

object TemplateMod : ModInitializer {

    val MOD_ID = "template"
    val LOGGER: Logger = LogUtils.getLogger()

    override fun onInitialize() {
        BlocksRegistry
        EnchantmentsRegistry
        ItemsRegistry
        LOGGER.info("${javaClass.simpleName} initialized")
    }
}