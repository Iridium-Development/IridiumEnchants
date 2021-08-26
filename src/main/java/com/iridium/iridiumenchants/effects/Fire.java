package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;

public class Fire implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args) {
        int time;
        try {
            time = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            time = 20;
        }
        if (args.length == 3 && args[2].equalsIgnoreCase("target")) {
            target.setFireTicks(time);
        } else {
            player.setFireTicks(time);
        }
    }
}
