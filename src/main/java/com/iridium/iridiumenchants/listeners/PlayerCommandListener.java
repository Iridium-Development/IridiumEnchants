package com.iridium.iridiumenchants.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        ItemStack currentItem = event.getPlayer().getItemInHand();
        if (currentItem.getItemMeta() != null) {
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
