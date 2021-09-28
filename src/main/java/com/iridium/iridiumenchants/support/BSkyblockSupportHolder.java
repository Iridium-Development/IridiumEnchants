package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;

import java.util.function.Supplier;

public class BSkyblockSupportHolder implements BuildSupportHolder, FriendlySupportHolder {
    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("BentoBox");
    }

    @Override
    public Supplier<FriendlySupport> friendlySupport() {
        return BSkyblockSupport::new;
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return BSkyblockSupport::new;
    }
}
