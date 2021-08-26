package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;

public interface Effect {
    void apply(LivingEntity player, LivingEntity target, String[] args);
}
