package com.iridium.iridiumenchants.support;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionsSupport implements BuildSupport, FriendlySupport {
    @Override
    public boolean isInstalled() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
        return plugin != null && !plugin.getDescription().getAuthors().contains("drtshock");
    }

    @Override
    public boolean canBuild(Player player, Location location) {
        Faction P = MPlayer.get(player).getFaction();
        Faction B = BoardColl.get().getFactionAt(PS.valueOf(location));
        return (ChatColor.stripColor(B.getName()).equalsIgnoreCase("Wilderness")) || (P == B);
    }

    @Override
    public boolean isFriendly(LivingEntity player, LivingEntity livingEntity) {
        if(player instanceof Player && livingEntity instanceof Player){
            Faction p = MPlayer.get(player).getFaction();
            Faction o = MPlayer.get(livingEntity).getFaction();
            Rel r = MPlayer.get(player).getRelationTo(MPlayer.get(livingEntity));
            return r.isFriend() || p == o;
        }
        return false;
    }
}
