package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class Potion implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
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
            if (target == null) return;
            // This is dumb, but 1.8 doesnt include Player#getPotionEffect
            Optional<PotionEffect> potionEffect = target.getActivePotionEffects().stream()
                    .filter(effect -> effect.getType().equals(potionEffectType))
                    .findFirst();
            if (potionEffect.isPresent()) {
                if (potionEffect.get().getAmplifier() <= amplifier && potionEffect.get().getDuration() <= duration * 20) {
                    target.removePotionEffect(potionEffectType);
                }
            }
            target.addPotionEffect(potionEffectType.createEffect(duration * 20, amplifier));
        } else {
            if (player == null) return;
            if (target == null) return;
            // This is dumb, but 1.8 doesnt include Player#getPotionEffect
            Optional<PotionEffect> potionEffect = player.getActivePotionEffects().stream()
                    .filter(effect -> effect.getType().equals(potionEffectType))
                    .findFirst();
            if (potionEffect.isPresent()) {
                if (potionEffect.get().getAmplifier() <= amplifier && potionEffect.get().getDuration() <= duration * 20) {
                    player.removePotionEffect(potionEffectType);
                }
            }
            player.addPotionEffect(potionEffectType.createEffect(duration * 20, amplifier));
        }
    }
}
