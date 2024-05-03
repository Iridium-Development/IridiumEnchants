package com.iridium.iridiumenchants.support;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import com.iridium.iridiumteams.PermissionType;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class IridiumSkyblockSupport implements BuildSupport, FriendlySupport {
    @Override
    public boolean canBuild(Player player, Location location) {
        User user = IridiumSkyblockAPI.getInstance().getUser(player);
        return IridiumSkyblockAPI.getInstance().getIslandViaLocation(location)
                .map(island -> IridiumSkyblockAPI.getInstance().getIslandPermission(island, user, PermissionType.BLOCK_BREAK))
                .orElse(false);
    }

    @Override
    public boolean isFriendly(LivingEntity player, LivingEntity livingEntity) {
        if (player instanceof Player && livingEntity instanceof Player) {
            User user1 = IridiumSkyblockAPI.getInstance().getUser((Player) player);
            User user = IridiumSkyblockAPI.getInstance().getUser((Player) livingEntity);
            return user1.getIsland().map(Island::getId).orElse(-1).equals(user.getIsland().map(Island::getId).orElse(-2));
        }
        return true;
    }
}
