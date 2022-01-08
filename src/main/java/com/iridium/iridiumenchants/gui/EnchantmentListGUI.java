package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.configs.inventories.NoItemGUI;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class EnchantmentListGUI extends PagedGUI<Map.Entry<String, CustomEnchant>> {
    private final int page;

    public EnchantmentListGUI(int page) {
        super(page, IridiumEnchants.getInstance().getInventories().enchantsListGUI.background, IridiumEnchants.getInstance().getInventories().previousPage, IridiumEnchants.getInstance().getInventories().nextPage);
        this.page = page;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = IridiumEnchants.getInstance().getInventories().enchantsListGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public ItemStack getItemStack(Map.Entry<String, CustomEnchant> customEnchantEntry) {
        return ItemStackUtils.makeItem(IridiumEnchants.getInstance().getInventories().enchantsListGUI.item, Arrays.asList(
                new Placeholder("enchant_name", WordUtils.capitalize(customEnchantEntry.getKey())),
                new Placeholder("enchant_type", WordUtils.capitalize(customEnchantEntry.getValue().type)),
                new Placeholder("enchant_description", customEnchantEntry.getValue().description)
        ));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlot() == getInventory().getSize() - 7) {
            if (page > 1) {
                event.getWhoClicked().openInventory(new EnchantmentListGUI(page - 1).getInventory());
            }
        } else if (event.getSlot() == getInventory().getSize() - 3) {
            if ((event.getInventory().getSize() - 9) * page < getPageObjects().size()) {
                event.getWhoClicked().openInventory(new EnchantmentListGUI(page + 1).getInventory());
            }
        }
    }

    @Override
    public Collection<Map.Entry<String, CustomEnchant>> getPageObjects() {
        return IridiumEnchants.getInstance().getCustomEnchants().customEnchants.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
    }
}
