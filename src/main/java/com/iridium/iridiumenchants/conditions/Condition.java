package com.iridium.iridiumenchants.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface Condition {
    boolean apply(LivingEntity player, LivingEntity target, String[] args, ItemStack item);
}
