package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinLeaveListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        IridiumEnchants.getInstance().getUserManager().getUser(event.getPlayer());
    }

}
