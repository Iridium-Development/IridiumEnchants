package com.iridium.iridiumenchants;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IridiumEnchant extends Enchantment {
    private final CustomEnchant customEnchant;

    public IridiumEnchant(@NotNull String key, @NotNull CustomEnchant customEnchant) {
        super(NamespacedKey.minecraft(key.toLowerCase().replace(" ", "")));
        this.customEnchant = customEnchant;
    }

    @NotNull
    @Override
    public String getName() {
        return customEnchant.displayName;
    }

    @Override
    public int getMaxLevel() {
        return customEnchant.levels.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    @Override
    public int getStartLevel() {
        return customEnchant.levels.keySet().stream().min(Integer::compareTo).orElse(0);
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return customEnchant.type;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return getItemTarget().includes(item);
    }

    public CustomEnchant getCustomEnchant() {
        return customEnchant;
    }
}
