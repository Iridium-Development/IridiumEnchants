package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;

import java.util.function.Supplier;

public class IridiumSkyblockSupportHolder implements BuildSupportHolder, FriendlySupportHolder {
    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("IridiumSkyblock");
    }

    @Override
    public Supplier<FriendlySupport> friendlySupport() {
        return IridiumSkyblockSupport::new;
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return IridiumSkyblockSupport::new;
    }
}
