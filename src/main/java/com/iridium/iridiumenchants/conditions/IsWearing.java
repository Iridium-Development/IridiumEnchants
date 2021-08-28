package com.iridium.iridiumenchants.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IsWearing implements Condition {
    @Override
    public boolean apply(LivingEntity player, LivingEntity target, String[] args, ItemStack item) {
        if (!(player instanceof Player)) return false;
        for (ItemStack armor : ((Player) player).getInventory().getArmorContents()) {
            if (armor == null) continue;
            if (armor.equals(item)) return true;
        }
        return false;
    }
}
