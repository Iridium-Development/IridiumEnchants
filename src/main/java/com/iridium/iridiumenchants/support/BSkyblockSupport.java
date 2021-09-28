package com.iridium.iridiumenchants.support;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.lists.Flags;

public class BSkyblockSupport implements BuildSupport, FriendlySupport {
    @Override
    public boolean canBuild(Player player, Location location) {
        return BentoBox.getInstance().getIslandsManager().getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.BREAK_BLOCKS))
                .orElse(true);
    }

    @Override
    public boolean isFriendly(LivingEntity player, LivingEntity livingEntity) {
        Island island = BentoBox.getInstance().getIslands().getIsland(player.getWorld(), player.getUniqueId());
        if (island == null) return false;
        return island.getMembers().containsKey(livingEntity.getUniqueId());
    }
}
