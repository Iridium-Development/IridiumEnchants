package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.GKit;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.configs.inventories.NoItemGUI;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public class GKitsPreviewGUI extends PagedGUI<ItemStack> {
    private final Map.Entry<String, GKit> gKit;

    public GKitsPreviewGUI(Map.Entry<String, GKit> gKit) {
        super(1, IridiumEnchants.getInstance().getInventories().gkitsPreview.size, IridiumEnchants.getInstance().getInventories().gkitsPreview.background, IridiumEnchants.getInstance().getInventories().previousPage, IridiumEnchants.getInstance().getInventories().nextPage);
        this.gKit = gKit;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = IridiumEnchants.getInstance().getInventories().gkitsPreview;
        Inventory inventory = Bukkit.createInventory(this, getSize(), StringUtils.color(noItemGUI.title.replace("%gkit%", gKit.getKey())));
        addContent(inventory);
        return inventory;
    }

    @Override
    public Collection<ItemStack> getPageObjects() {
        return IridiumEnchants.getInstance().getGkitsManager().getItemsFromGkit(gKit.getValue());
    }

    @Override
    public ItemStack getItemStack(ItemStack itemStack) {
        return itemStack;
    }
}