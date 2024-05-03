package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.EnchantMethod;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.Type;
import com.iridium.iridiumenchants.utils.EnchantmentUtils;
import com.iridium.iridiumenchants.utils.TypeUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

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
            if (IridiumEnchants.getInstance().getConfiguration().enchantMethod == EnchantMethod.DRAGNDROP) {
                IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentFromCrystal(event.getCursor()).ifPresent(iridiumEnchant -> {
                    CustomEnchant customEnchant = IridiumEnchants.getInstance().getCustomEnchants().customEnchants.get(iridiumEnchant);
                    if (customEnchant == null) return;
                    if (currentItem == null) return;
                    Optional<Type> type = TypeUtils.getType(customEnchant.type);
                    if (!type.isPresent()) return;
                    int level = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentLevelFromCrystal(event.getWhoClicked().getItemInHand());
                    if (IridiumEnchants.getInstance().getCustomEnchantManager().canApply(currentItem, iridiumEnchant, level, type.get())) {
                        event.setCancelled(true);
                        event.getClickedInventory().setItem(event.getSlot(), IridiumEnchants.getInstance().getCustomEnchantManager().applyEnchantment(currentItem, iridiumEnchant, customEnchant, level));
                        if (event.getCursor().getAmount() > 1) {
                            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                        } else {
                            event.setCursor(null);
                        }
                    }
                });
            }

            if (currentItem != null) {
                EnchantmentUtils.removeEnchantmentEffect(currentItem);
            }
        }
    }
}
