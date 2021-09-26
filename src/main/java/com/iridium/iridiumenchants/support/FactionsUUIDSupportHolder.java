package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;

public class FactionsUUIDSupportHolder implements BuildSupportHolder, FriendlySupportHolder {
    @Override
    public boolean isInstalled() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
        return plugin != null && plugin.getDescription().getAuthors().contains("drtshock");
    }

    @Override
    public Supplier<FriendlySupport> friendlySupport() {
        return FactionsSupport::new;
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return FactionsSupport::new;
    }
}
