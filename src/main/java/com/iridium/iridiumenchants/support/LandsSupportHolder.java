package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;

import java.util.function.Supplier;

public class LandsSupportHolder implements BuildSupportHolder, FriendlySupportHolder {
    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("Lands");
    }

    @Override
    public Supplier<FriendlySupport> friendlySupport() {
        return LandsSupport::new;
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return LandsSupport::new;
    }
}
