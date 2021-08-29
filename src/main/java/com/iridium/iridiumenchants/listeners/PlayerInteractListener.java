package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.gui.EnchantmentSelectGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInHand();
            IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentFromCrystal(itemStack).ifPresent(iridiumEnchant -> {
                        CustomEnchant customEnchant = IridiumEnchants.getInstance().getCustomEnchants().customEnchants.get(iridiumEnchant);
                        if (customEnchant == null) return;
                        player.openInventory(new EnchantmentSelectGUI(player, customEnchant, iridiumEnchant).getInventory());
                    }
            );
        }
    }

}
