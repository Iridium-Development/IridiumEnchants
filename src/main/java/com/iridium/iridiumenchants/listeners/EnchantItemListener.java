package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.utils.EnchantmentUtils;
import com.iridium.iridiumenchants.utils.TypeUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

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
            if (TypeUtils.getType(customEnchant.getValue().type).map(type -> type.includes(item.getType())).orElse(false) && customEnchant.getValue().enchantmentTable) {
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
        EnchantmentUtils.removeEnchantmentEffect(item);
    }

}
