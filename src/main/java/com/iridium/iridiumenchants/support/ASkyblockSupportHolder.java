package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;

import java.util.function.Supplier;

public class ASkyblockSupportHolder implements BuildSupportHolder, FriendlySupportHolder {
    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("ASkyBlock");
    }

    @Override
    public Supplier<FriendlySupport> friendlySupport() {
        return ASkyblockSupport::new;
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return ASkyblockSupport::new;
    }
}
