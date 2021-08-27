package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

public class Potion implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        if (args.length < 4) return;
        PotionEffectType potionEffectType = PotionEffectType.getByName(args[1]);
        if (potionEffectType == null) return;
        int amplifier;
        try {
            amplifier = Integer.parseInt(args[2]) - 1;
        } catch (NumberFormatException exception) {
            amplifier = 0;
        }
        int duration;
        try {
            duration = Integer.parseInt(args[3]);
        } catch (NumberFormatException exception) {
            duration = 1;
        }
        if (args.length == 5 && args[4].equalsIgnoreCase("target")) {
            if(target==null)return;
            target.addPotionEffect(potionEffectType.createEffect(duration * 20, amplifier));
        } else {
            if(player==null)return;
            player.addPotionEffect(potionEffectType.createEffect(duration * 20, amplifier));
        }
    }
}
