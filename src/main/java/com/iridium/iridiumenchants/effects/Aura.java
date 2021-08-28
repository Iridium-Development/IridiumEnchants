package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.support.FriendlySupport;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import java.util.Arrays;

public class Aura implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
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
            if (auraType.isValid(player, livingEntity, IridiumEnchants.getInstance().getFriendlySupport())) {
                Effect effect = IridiumEnchants.getInstance().getEffects().get(newArgs[0]);
                if (effect != null) {
                    effect.apply(player, livingEntity, newArgs, event);
                }
            }
        }
    }

    public enum AuraType {
        ALLY {
            @Override
            public boolean isValid(LivingEntity player, LivingEntity livingEntity, FriendlySupport friendlySupport) {
                return friendlySupport.isFriendly(player, livingEntity);
            }
        }, ENEMY {
            @Override
            public boolean isValid(LivingEntity player, LivingEntity livingEntity, FriendlySupport friendlySupport) {
                return !friendlySupport.isFriendly(player, livingEntity);
            }
        }, ALL {
            @Override
            public boolean isValid(LivingEntity player, LivingEntity livingEntity, FriendlySupport friendlySupport) {
                return true;
            }
        };

        public abstract boolean isValid(LivingEntity player, LivingEntity livingEntity, FriendlySupport friendlySupport);

    }
}
