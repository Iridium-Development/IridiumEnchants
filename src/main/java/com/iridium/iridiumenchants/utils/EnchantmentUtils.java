package com.iridium.iridiumenchants.utils;

import com.iridium.iridiumcore.dependencies.xseries.XEnchantment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class EnchantmentUtils {

    public static void addEnchantmentEffect(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;
        itemStack.setItemMeta(addEnchantmentEffect(itemMeta, itemStack.getType()));
    }

    public static ItemMeta addEnchantmentEffect(ItemMeta itemMeta, Material material) {
        if (!itemMeta.hasEnchants()) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (material.equals(Material.FISHING_ROD)) {
                Optional.ofNullable(XEnchantment.ARROW_FIRE.getEnchant()).ifPresent(enchantment -> itemMeta.addEnchant(enchantment, 1, false));
            } else {
                Optional.ofNullable(XEnchantment.LURE.getEnchant()).ifPresent(enchantment -> itemMeta.addEnchant(enchantment, 1, false));
            }
        }
        return itemMeta;
    }

    public static ItemMeta removeEnchantmentEffect(ItemMeta itemMeta, Material material) {
        if (itemMeta.hasEnchants()) {
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (material.equals(Material.FISHING_ROD)) {
                Optional.ofNullable(XEnchantment.ARROW_FIRE.getEnchant()).ifPresent(itemMeta::removeEnchant);
            } else {
                Optional.ofNullable(XEnchantment.LURE.getEnchant()).ifPresent(itemMeta::removeEnchant);
            }
        }
        return itemMeta;
    }

    public static void removeEnchantmentEffect(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;
        itemStack.setItemMeta(removeEnchantmentEffect(itemMeta, itemStack.getType()));
    }

}
