package com.iridium.iridiumenchants.support;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface BuildSupport {
    boolean isInstalled();
    boolean canBuild(Player player, Location location);
}
