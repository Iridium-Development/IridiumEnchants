package com.iridium.iridiumenchants.support;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;

public class WorldGuard7SupportHolder implements BuildSupportHolder {
    @Override
    public boolean isInstalled() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (plugin == null) return false;
        return plugin.getDescription().getVersion().startsWith("7");
    }

    @Override
    public Supplier<BuildSupport> buildSupport() {
        return WorldGuard7Support::new;
    }
}
