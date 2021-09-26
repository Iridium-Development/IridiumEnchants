package com.iridium.iridiumenchants.support;

import org.bukkit.entity.LivingEntity;

public interface FriendlySupport {
    boolean isInstalled();
    boolean isFriendly(LivingEntity player, LivingEntity livingEntity);
}
