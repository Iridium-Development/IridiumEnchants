package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.utils.EnchantmentUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        ItemStack currentItem = event.getPlayer().getItemInHand();
        if (currentItem != null) {
            EnchantmentUtils.removeEnchantmentEffect(currentItem);
        }
    }

}
