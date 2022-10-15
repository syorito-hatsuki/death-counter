package dev.syoritohatsuki.template.registry

import dev.syoritohatsuki.template.TemplateMod
import dev.syoritohatsuki.template.block.TemplateBlock
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object BlocksRegistry {
    private val BLOCKS: MutableMap<Block, Identifier> = LinkedHashMap()

    val TEMPLATE_BLOCK = TemplateBlock().create("template_block")

    init {
        BLOCKS.keys.forEach {
            Registry.register(Registry.BLOCK, BLOCKS[it], it)
            Registry.register(Registry.ITEM, BLOCKS[it], BlockItem(it, Item.Settings().group(ItemGroup.MISC)))
        }
    }

    private fun Block.create(name: String): Block {
        BLOCKS[this] = Identifier(TemplateMod.MOD_ID, name)
        return this
    }
}