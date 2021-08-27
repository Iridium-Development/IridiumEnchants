package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Experience implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        int experience;
        try {
            experience = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            experience = 0;
        }
        if (args.length == 3 && args[2].equalsIgnoreCase("target")) {
            if (target instanceof Player) {
                ((Player) target).giveExp(experience);
            }
        } else {
            if (player instanceof Player) {
                ((Player) player).giveExp(experience);
            }
        }
    }
}
