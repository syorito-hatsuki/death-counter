package dev.syoritohatsuki.template.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot

class TemplateEnchantment : Enchantment(
    Rarity.COMMON,
    EnchantmentTarget.WEAPON,
    arrayOf(EquipmentSlot.MAINHAND)
) {
    override fun getMinPower(level: Int): Int = level * 25

    override fun getMaxPower(level: Int): Int = getMinPower(level) + 50

    override fun getMaxLevel(): Int = 1
}