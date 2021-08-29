package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.GKit;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.configs.inventories.NoItemGUI;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class GKitsPreviewGUI implements GUI {
    private final Map.Entry<String, GKit> gKit;

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = IridiumEnchants.getInstance().getInventories().gkitsPreview;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title.replace("%gkit%", gKit.getKey())));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        InventoryUtils.fillInventory(inventory, IridiumEnchants.getInstance().getInventories().gkitsPreview.background);
        AtomicInteger slot = new AtomicInteger(0);
        for (ItemStack itemStack : IridiumEnchants.getInstance().getGkitsManager().getItemsFromGkit(gKit.getValue())) {
            int currentSlot = slot.getAndIncrement();
            if (inventory.getSize() > currentSlot) inventory.setItem(currentSlot, itemStack);
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }
}
