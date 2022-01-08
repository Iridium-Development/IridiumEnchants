package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@AllArgsConstructor
public class EnchantmentSelectGUI implements GUI {

    private final Player player;
    private final CustomEnchant customEnchant;
    private final String enchantKey;

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.getServer().createInventory(this, 36, StringUtils.color(IridiumEnchants.getInstance().getInventories().enchantmentSelectGUITitle));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = player.getInventory().getContents()[i];
            if (itemStack != null) {
                if (IridiumEnchants.getInstance().getTypes().types.get(customEnchant.type).includes(itemStack.getType())) {
                    inventory.setItem(i, itemStack);
                }
            }
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack itemStack = player.getInventory().getContents()[event.getSlot()];
        if (IridiumEnchants.getInstance().getTypes().types.get(customEnchant.type).includes(itemStack.getType())) {
            //Double check they still have the enchantment crystal in their hand
            Optional<String> iridiumEnchant = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentFromCrystal(event.getWhoClicked().getItemInHand());
            if (iridiumEnchant.isPresent() && iridiumEnchant.get().equals(enchantKey)) {
                int level = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentLevelFromCrystal(event.getWhoClicked().getItemInHand());
                ItemStack item = IridiumEnchants.getInstance().getCustomEnchantManager().applyEnchantment(itemStack, enchantKey, customEnchant, level);
                player.getInventory().setItem(event.getSlot(), item);
                event.getWhoClicked().closeInventory();
                int amount = event.getWhoClicked().getItemInHand().getAmount();
                if (amount > 1) {
                    event.getWhoClicked().getItemInHand().setAmount(amount - 1);
                } else {
                    event.getWhoClicked().setItemInHand(null);
                }
            } else {
                event.getWhoClicked().closeInventory();
            }
        }
    }
}
