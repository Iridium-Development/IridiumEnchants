package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Aura implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        AuraType auraType = AuraType.valueOf(args[1].toUpperCase());
        int range;
        try {
            range = Integer.parseInt(args[2]);
        } catch (NumberFormatException exception) {
            range = 1;
        }
        String[] newArgs = Arrays.copyOfRange(args, 3, args.length);
        for (Entity entity : player.getNearbyEntities(range, range, range)) {
            if (!(entity instanceof LivingEntity)) continue;
            LivingEntity livingEntity = (LivingEntity) entity;
            if (auraType.isValid(player, livingEntity)) {
                Effect effect = IridiumEnchants.getInstance().getEffects().get(newArgs[0]);
                if (effect != null) {
                    effect.apply(player, livingEntity, newArgs, itemStack, event);
                }
            }
        }
    }

    public enum AuraType {
        ALLY {
            @Override
            public boolean isValid(LivingEntity player, LivingEntity livingEntity) {
                return IridiumEnchants.getInstance().isFriendly(player, livingEntity);
            }
        }, ENEMY {
            @Override
            public boolean isValid(LivingEntity player, LivingEntity livingEntity) {
                return !IridiumEnchants.getInstance().isFriendly(player, livingEntity);
            }
        }, ALL {
            @Override
            public boolean isValid(LivingEntity player, LivingEntity livingEntity) {
                return true;
            }
        };

        public abstract boolean isValid(LivingEntity player, LivingEntity livingEntity);

    }
}
