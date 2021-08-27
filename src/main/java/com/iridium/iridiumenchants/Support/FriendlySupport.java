package com.iridium.iridiumenchants.Support;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface FriendlySupport {
    boolean isFriendly(Player player, LivingEntity livingEntity);
}
