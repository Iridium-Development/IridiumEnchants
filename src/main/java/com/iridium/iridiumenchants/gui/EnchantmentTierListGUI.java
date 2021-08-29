package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.Level;
import com.iridium.iridiumenchants.configs.inventories.NoItemGUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EnchantmentTierListGUI extends PagedGUI<EnchantmentTierListGUI.CustomEnchantLevel> {
    private final int page;
    private final String tier;

    public EnchantmentTierListGUI(int page, String tier) {
        super(page, IridiumEnchants.getInstance().getInventories().enchantsTierListGUI.background, IridiumEnchants.getInstance().getInventories().previousPage, IridiumEnchants.getInstance().getInventories().nextPage);
        this.page = page;
        this.tier = tier;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = IridiumEnchants.getInstance().getInventories().enchantsTierListGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title.replace("%tier%", tier)));
        addContent(inventory);
        return inventory;
    }

    @Override
    public ItemStack getItemStack(CustomEnchantLevel customEnchantLevel) {
        return ItemStackUtils.makeItem(IridiumEnchants.getInstance().getInventories().enchantsTierListGUI.item, Arrays.asList(
                new Placeholder("enchant_name", WordUtils.capitalize(customEnchantLevel.customEnchant.getKey())),
                new Placeholder("enchant_type", WordUtils.capitalize(customEnchantLevel.customEnchant.getValue().type.name().toLowerCase())),
                new Placeholder("enchant_description", customEnchantLevel.customEnchant.getValue().description),
                new Placeholder("enchant_level", IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(customEnchantLevel.level.getKey()))
        ));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlot() == getInventory().getSize() - 7) {
            if (page > 1) {
                event.getWhoClicked().openInventory(new EnchantmentTierListGUI(page - 1, tier).getInventory());
            }
        } else if (event.getSlot() == getInventory().getSize() - 3) {
            if ((event.getInventory().getSize() - 9) * page < getPageObjects().size()) {
                event.getWhoClicked().openInventory(new EnchantmentTierListGUI(page + 1, tier).getInventory());
            }
        }
    }

    @Override
    public Collection<CustomEnchantLevel> getPageObjects() {
        List<CustomEnchantLevel> customEnchantLevelList = new ArrayList<>();
        for (Map.Entry<String, CustomEnchant> customEnchant : IridiumEnchants.getInstance().getCustomEnchants().customEnchants.entrySet()) {
            for (Map.Entry<Integer, Level> level : customEnchant.getValue().levels.entrySet()) {
                if (level.getValue().tiers.contains(tier)) {
                    customEnchantLevelList.add(new CustomEnchantLevel(customEnchant, level));
                }
            }
        }
        return customEnchantLevelList;
    }

    @AllArgsConstructor
    @Getter
    public static class CustomEnchantLevel {
        private final Map.Entry<String, CustomEnchant> customEnchant;
        private final Map.Entry<Integer, Level> level;
    }
}
