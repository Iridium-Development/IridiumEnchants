package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnchantItemListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnchantItem(EnchantItemEvent event) {
        if (!IridiumEnchants.getInstance().getConfiguration().enchantmentTable) return;
        if (event.getExpLevelCost() != 30) return;
        ItemStack item = event.getItem();
        List<Map.Entry<String, CustomEnchant>> customEnchants = new ArrayList<>();
        for (Map.Entry<String, CustomEnchant> customEnchant : IridiumEnchants.getInstance().getCustomEnchants().customEnchants.entrySet()) {
            if (IridiumEnchants.getInstance().getTypes().types.get(customEnchant.getValue().type).includes(item.getType()) && customEnchant.getValue().enchantmentTable) {
                customEnchants.add(customEnchant);
            }
        }
        Random random = new Random();
        int min = IridiumEnchants.getInstance().getConfiguration().enchantingTableMin;
        int max = IridiumEnchants.getInstance().getConfiguration().enchantingTableMax;
        int amount = random.nextInt(max - min) + min;
        for (int i = 0; i < amount; i++) {
            if (!customEnchants.isEmpty()) {
                Map.Entry<String, CustomEnchant> customEnchant = customEnchants.get(random.nextInt(customEnchants.size()));
                List<Integer> levels = new ArrayList<>(customEnchant.getValue().levels.keySet());
                int level = levels.get(random.nextInt(levels.size()));
                item.setItemMeta(IridiumEnchants.getInstance().getCustomEnchantManager().applyEnchantment(item, customEnchant.getKey(), customEnchant.getValue(), level).getItemMeta());
            }
        }
        // Remove the fake glow effect
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (item.getType().equals(Material.FISHING_ROD)) {
                itemMeta.removeEnchant(Enchantment.ARROW_FIRE);
            } else {
                itemMeta.removeEnchant(Enchantment.LURE);
            }
            item.setItemMeta(itemMeta);
        }
    }

}
