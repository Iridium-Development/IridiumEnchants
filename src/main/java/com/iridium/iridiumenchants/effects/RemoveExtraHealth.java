package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.User;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class RemoveExtraHealth implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        //AddHealth:1:20:TARGET
        if (args.length < 3) return;
        int amount;
        int time;
        try {
            amount = Integer.parseInt(args[1]);
            time = Integer.parseInt(args[2]);
        } catch (NumberFormatException exception) {
            amount = 1;
            time = 20;
        }
        User user;
        if (args.length == 4 && args[3].equalsIgnoreCase("target")) {
            user = IridiumEnchants.getInstance().getUserManager().getUser(target);
        } else {
            user = IridiumEnchants.getInstance().getUserManager().getUser(player);
        }
        user.setUserHealthKey(itemStack, -amount, time);
    }
}
