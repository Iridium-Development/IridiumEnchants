package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.GUI;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EnchantmentSelectGUI implements GUI {

    private final Player player;
    private final IridiumEnchant iridiumEnchant;

    public EnchantmentSelectGUI(Player player, IridiumEnchant iridiumEnchant) {
        this.player = player;
        this.iridiumEnchant = iridiumEnchant;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.getServer().createInventory(this, 36, StringUtils.color("&8CustomEnchants"));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        for (int i = 0; i < player.getInventory().getStorageContents().length; i++) {
            ItemStack itemStack = player.getInventory().getStorageContents()[i];
            if (itemStack != null) {
                if (iridiumEnchant.getCustomEnchant().type.includes(itemStack)) {
                    inventory.setItem(i, itemStack);
                }
            }
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack itemStack = player.getInventory().getContents()[event.getSlot()];
        if (iridiumEnchant.getCustomEnchant().type.includes(itemStack)) {
            //Double check they still have the enchantment crystal in their hand
            Optional<IridiumEnchant> iridiumEnchantOptional = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentFromCrystal(event.getWhoClicked().getItemInHand());
            if (iridiumEnchantOptional.isPresent() && iridiumEnchantOptional.get() == iridiumEnchant) {
                int level = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentLevelFromCrystal(event.getWhoClicked().getItemInHand());
                IridiumEnchants.getInstance().getCustomEnchantManager().applyEnchantment(itemStack, iridiumEnchant, level);
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
