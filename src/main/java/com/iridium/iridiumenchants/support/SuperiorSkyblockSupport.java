package com.iridium.iridiumenchants.support;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SuperiorSkyblockSupport implements BuildSupport, FriendlySupport {
    @Override
    public boolean canBuild(Player player, Location location) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
        if (island == null) return true;
        return island.hasPermission(superiorPlayer, IslandPrivilege.getByName("Break"));
    }

    @Override
    public boolean isFriendly(LivingEntity player, LivingEntity livingEntity) {
        return SuperiorSkyblockAPI.getPlayer(player.getUniqueId()).getIsland() == SuperiorSkyblockAPI.getPlayer(livingEntity.getUniqueId()).getIsland();
    }
}
