package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;

import java.util.function.Supplier;

public class TownySupportHolder implements BuildSupportHolder, FriendlySupportHolder {
    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("TownyAdvanced");
    }

    @Override
    public Supplier<FriendlySupport> friendlySupport() {
        return TownySupport::new;
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return TownySupport::new;
    }
}
