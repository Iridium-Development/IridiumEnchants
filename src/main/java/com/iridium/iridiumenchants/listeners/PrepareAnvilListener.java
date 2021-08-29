package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PrepareAnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        if (event.getResult() == null) return;
        ItemStack result = event.getResult();
        ItemStack slot1 = event.getInventory().getItem(0);
        ItemStack slot2 = event.getInventory().getItem(1);
        if (slot1 == null || slot2 == null) return;
        for (Map.Entry<String, Integer> enchant : IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentsFromItem(slot1).entrySet()) {
            CustomEnchant customEnchant = IridiumEnchants.getInstance().getCustomEnchants().customEnchants.get(enchant.getKey());
            if (customEnchant != null) {
                result = IridiumEnchants.getInstance().getCustomEnchantManager().applyEnchantment(result, enchant.getKey(), customEnchant, enchant.getValue());
            }
        }
        for (Map.Entry<String, Integer> enchant : IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentsFromItem(slot2).entrySet()) {
            CustomEnchant customEnchant = IridiumEnchants.getInstance().getCustomEnchants().customEnchants.get(enchant.getKey());
            if (customEnchant != null) {
                result = IridiumEnchants.getInstance().getCustomEnchantManager().applyEnchantment(result, enchant.getKey(), customEnchant, enchant.getValue());
            }
        }
        event.setResult(result);
    }

}
