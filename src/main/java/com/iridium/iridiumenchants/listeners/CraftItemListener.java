package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftItemListener implements Listener {

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event){
        for (ItemStack item : event.getInventory().getMatrix()) {
            if(IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentFromCrystal(item).isPresent()){
                event.getInventory().setResult(null);
            }
        }
    }

}
