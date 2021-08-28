package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.Tier;
import com.iridium.iridiumenchants.configs.inventories.AnimatedBackgroundGUI;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class EnchantmentTierGUI implements GUI {

    private int frame = 0;
    private int tickCount = 0;

    @NotNull
    @Override
    public Inventory getInventory() {
        AnimatedBackgroundGUI animatedBackgroundGUI = IridiumEnchants.getInstance().getInventories().enchantsTierGUI;
        Inventory inventory = Bukkit.createInventory(this, animatedBackgroundGUI.size, StringUtils.color(animatedBackgroundGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        Background background = IridiumEnchants.getInstance().getInventories().enchantsTierGUI.background.backgroundFrames.get(frame);
        if (background != null) {
            InventoryUtils.fillInventory(inventory, background);
        }

        for (Tier tier : IridiumEnchants.getInstance().getConfiguration().tiers.values()) {
            inventory.setItem(tier.item.slot, ItemStackUtils.makeItem(tier.item, Collections.singletonList(
                    new Placeholder("cost", String.valueOf(tier.experienceCost))
            )));
        }

        tickCount++;
        if (tickCount % IridiumEnchants.getInstance().getInventories().enchantsTierGUI.nextFrameInterval == 0) {
            frame++;
            if (IridiumEnchants.getInstance().getInventories().enchantsTierGUI.background.backgroundFrames.get(frame) == null) {
                frame = 0;
            }
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }
}
