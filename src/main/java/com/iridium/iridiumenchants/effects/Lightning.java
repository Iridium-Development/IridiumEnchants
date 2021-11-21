package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class Lightning implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (args.length == 2 && args[1].equalsIgnoreCase("target")) {
            target.getWorld().strikeLightning(target.getLocation());
        } else {
            player.getWorld().strikeLightning(player.getLocation());
        }
    }
}
