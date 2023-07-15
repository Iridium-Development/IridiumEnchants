package com.iridium.iridiumenchants.support;

import com.iridium.iridiumenchants.IridiumEnchants;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.land.Area;
import me.angeschossen.lands.api.land.LandWorld;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.player.LandPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;


public class LandsSupport implements BuildSupport, FriendlySupport {

    LandsIntegration api = LandsIntegration.of(IridiumEnchants.getInstance());

    @Override
    public boolean canBuild(Player player, Location location) {

        LandPlayer landPlayer = api.getLandPlayer(player.getUniqueId());
        LandWorld world = api.getWorld(player.getWorld());
        Material heldItem = player.getItemInHand().getType();

        if (world != null) {

            if (world.hasRoleFlag(landPlayer, location, Flags.BLOCK_BREAK, heldItem, false)) {
                return false;
            }

            if (world.hasRoleFlag(landPlayer, location, Flags.BLOCK_PLACE, heldItem, false)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isFriendly(LivingEntity player, LivingEntity livingEntity) {

        LandPlayer landPlayer = api.getLandPlayer(player.getUniqueId());

        Location location = player.getLocation();
        Area area = api.getArea(location);

        if(area.getLand().isInWar()){
            return false;
        }

        if (!area.getTrustedPlayers().isEmpty()) {

            Collection<?> trustedPlayers = area.getTrustedPlayers();

            if (trustedPlayers.contains(landPlayer)) {
                return true;
            }

            if (player instanceof Player && livingEntity instanceof Player) {

                LandPlayer landPlayer2 = api.getLandPlayer(livingEntity.getUniqueId());

                boolean canPvP = api.canPvP(landPlayer.getPlayer(), landPlayer2.getPlayer(),
                        location, area.isTrusted(landPlayer.getUID()), area.isTrusted(landPlayer2.getUID()));

                if (canPvP) {
                    return false;
                }
            }
        }

        return false;
    }
}
