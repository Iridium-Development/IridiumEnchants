package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.gui.EnchantmentSelectGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInHand();
            for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                Bukkit.broadcastMessage(enchantment.getName());
            }
            if (itemStack.getType() == Material.AIR) return;
            IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentFromCrystal(itemStack).ifPresent(iridiumEnchant ->
                    player.openInventory(new EnchantmentSelectGUI(player, iridiumEnchant).getInventory())
            );
        }
    }

}
