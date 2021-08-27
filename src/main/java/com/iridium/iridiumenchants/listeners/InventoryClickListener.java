package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumcore.gui.GUI;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof GUI) {
            event.setCancelled(true);
            if (event.getClickedInventory() == event.getInventory()) {
                ((GUI) event.getInventory().getHolder()).onInventoryClick(event);
            }
        } else {
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && currentItem.getItemMeta() != null) {
                ItemMeta itemMeta = currentItem.getItemMeta();
                if (itemMeta.getEnchants().size() > 1) {
                    itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    if (currentItem.getType().equals(Material.FISHING_ROD)) {
                        itemMeta.removeEnchant(Enchantment.ARROW_FIRE);
                    } else {
                        itemMeta.removeEnchant(Enchantment.LURE);
                    }
                    currentItem.setItemMeta(itemMeta);
                }
            }
        }
    }
}
