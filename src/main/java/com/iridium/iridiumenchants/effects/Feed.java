package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Feed implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        if (args.length < 2) return;
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            amount = 1;
        }
        if (args.length == 3 && args[2].equalsIgnoreCase("target")) {
            if (target instanceof Player) {
                ((Player) target).setFoodLevel(((Player) target).getFoodLevel() + amount);
            }
        } else {
            if (player instanceof Player) {
                ((Player) player).setFoodLevel(((Player) player).getFoodLevel() + amount);
            }
        }
    }
}
