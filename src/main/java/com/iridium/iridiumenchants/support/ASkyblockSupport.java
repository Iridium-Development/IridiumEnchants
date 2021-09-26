package com.iridium.iridiumenchants.support;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ASkyblockSupport implements BuildSupport, FriendlySupport {
    @Override
    public boolean canBuild(Player player, Location location) {
        Island island = ASkyBlockAPI.getInstance().getIslandAt(location);
        if (island == null) return false;
        return island.getMembers().contains(player.getUniqueId());
    }

    @Override
    public boolean isFriendly(LivingEntity player, LivingEntity livingEntity) {
        Island island = ASkyBlockAPI.getInstance().getIslandOwnedBy(player.getUniqueId());
        if (island == null) return false;
        return island.getMembers().contains(livingEntity.getUniqueId());
    }
}
