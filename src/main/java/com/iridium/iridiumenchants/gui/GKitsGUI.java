package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.GKit;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.configs.inventories.AnimatedBackgroundGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GKitsGUI implements GUI {

    private int frame = 0;
    private int tickCount = 0;

    @NotNull
    @Override
    public Inventory getInventory() {
        AnimatedBackgroundGUI animatedBackgroundGUI = IridiumEnchants.getInstance().getInventories().gkitsGUI;
        Inventory inventory = Bukkit.createInventory(this, animatedBackgroundGUI.size, StringUtils.color(animatedBackgroundGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        Background background = IridiumEnchants.getInstance().getInventories().gkitsGUI.background.backgroundFrames.get(frame);
        if (background != null) {
            InventoryUtils.fillInventory(inventory, background);
        }

        for (GKit gKit : IridiumEnchants.getInstance().getGKits().gkits.values()) {
            inventory.setItem(gKit.guiItem.slot, ItemStackUtils.makeItem(gKit.guiItem));
        }

        tickCount++;
        if (tickCount % IridiumEnchants.getInstance().getInventories().gkitsGUI.nextFrameInterval == 0) {
            frame++;
            if (IridiumEnchants.getInstance().getInventories().gkitsGUI.background.backgroundFrames.get(frame) == null) {
                frame = 0;
            }
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        for (Map.Entry<String, GKit> gkit : IridiumEnchants.getInstance().getGKits().gkits.entrySet()) {
            if (gkit.getValue().guiItem.slot == event.getSlot()) {
                if (event.getClick() == ClickType.RIGHT) {
                    event.getWhoClicked().openInventory(new GKitsPreviewGUI(gkit).getInventory());
                } else {
                    IridiumEnchants.getInstance().getGkitsManager().getItemsFromGkit(gkit.getValue()).forEach(itemStack ->
                            player.getInventory().addItem(itemStack).values().forEach(item ->
                                    player.getWorld().dropItem(player.getLocation(), item)));
                }
            }
        }
    }
}
