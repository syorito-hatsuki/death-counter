package dev.syoritohatsuki.template.block

import dev.syoritohatsuki.template.TemplateMod
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.world.BlockView

class TemplateBlock(settings: Settings = FabricBlockSettings.of(Material.SOIL).strength(4f)) : Block(settings) {
    @Environment(EnvType.CLIENT)
    override fun appendTooltip(
        stack: ItemStack,
        world: BlockView?,
        tooltip: MutableList<Text>,
        options: TooltipContext
    ) {
        tooltip.add(Text.translatable("block.${TemplateMod.MOD_ID}.template_block.tooltip"))
    }
}