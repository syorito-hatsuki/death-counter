package dev.syoritohatsuki.template.registry

import dev.syoritohatsuki.template.TemplateMod.MOD_ID
import dev.syoritohatsuki.template.enchantment.TemplateEnchantment
import net.minecraft.enchantment.Enchantment
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object EnchantmentsRegistry {
    private val ENCHANTMENTS: MutableMap<Enchantment, Identifier> = LinkedHashMap()

    val TEMPLATE_ENCHANTMENT = TemplateEnchantment().create("template_enchantment")

    init {
        ENCHANTMENTS.keys.forEach {
            Registry.register(Registry.ENCHANTMENT, ENCHANTMENTS[it], it)
        }
    }

    private fun Enchantment.create(name: String): Enchantment {
        ENCHANTMENTS[this] = Identifier(MOD_ID, name)
        return this
    }
}