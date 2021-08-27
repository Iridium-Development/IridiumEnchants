package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Heal implements Effect {
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
            target.setHealth(target.getHealth() + amount);
        } else {
            player.setHealth(player.getHealth() + amount);
        }
    }
}
