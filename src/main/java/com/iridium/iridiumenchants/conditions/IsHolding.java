package com.iridium.iridiumenchants.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IsHolding implements Condition {
    @Override
    public boolean apply(LivingEntity player, LivingEntity target, String[] args, ItemStack item) {
        if (!(player instanceof Player)) return false;
        ItemStack hand = ((Player) player).getItemInHand();
        return hand.equals(item);
    }
}
