package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public interface Effect {
    void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event);
}
