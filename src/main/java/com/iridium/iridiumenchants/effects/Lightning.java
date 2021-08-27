package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public class Lightning implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        if (args.length == 2 && args[1].equalsIgnoreCase("target")) {
            target.getWorld().strikeLightning(target.getLocation());
        } else {
            player.getWorld().strikeLightning(player.getLocation());
        }
    }
}
