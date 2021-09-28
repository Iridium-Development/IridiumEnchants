package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;

import java.util.function.Supplier;

public class SuperiorSkyblockSupportHolder implements BuildSupportHolder, FriendlySupportHolder {
    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("SuperiorSkyblock2");
    }

    @Override
    public Supplier<FriendlySupport> friendlySupport() {
        return SuperiorSkyblockSupport::new;
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return SuperiorSkyblockSupport::new;
    }
}
