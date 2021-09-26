package com.iridium.iridiumenchants.support;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.TownyWorld;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class TownySupport implements BuildSupport, FriendlySupport {
    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("TownyAdvanced");
    }

    @Override
    public boolean canBuild(Player player, Location location) {
        TownyWorld world = TownyUniverse.getInstance().getWorldMap().get(location.getWorld().getName());
        if (world == null) {
            return true;
        }
        if (TownyAPI.getInstance().isWilderness(location)) {
            return true;
        }
        return PlayerCacheUtil.getCachePermission(player, location, location.getBlock().getType(), TownyPermission.ActionType.DESTROY);

    }

    @Override
    public boolean isFriendly(LivingEntity player, LivingEntity livingEntity) {
        Resident resident = TownyUniverse.getInstance().getResident(player.getUniqueId());
        Resident other = TownyUniverse.getInstance().getResident(livingEntity.getUniqueId());
        if (resident == null || other == null) return false;
        return resident.hasFriend(other);
    }
}
