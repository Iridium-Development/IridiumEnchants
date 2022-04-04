package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class Heal implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (args.length < 2) return;
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            amount = 1;
        }
        if (args.length == 3 && args[2].equalsIgnoreCase("target")) {
            target.setHealth((target.getHealth() +amount > 20) ? 20 : target.getHealth() + amount);
        } else {
            player.setHealth((player.getHealth() + amount > 20) ? 20 : player.getHealth() + amount);
        }
    }
}
