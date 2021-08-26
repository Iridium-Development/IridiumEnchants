package com.iridium.iridiumenchants.managers;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.nbtapi.NBTCompound;
import com.iridium.iridiumcore.dependencies.nbtapi.NBTItem;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomEnchantManager {

    /**
     * Converts the level to roman numerals
     *
     * @param level the level
     * @return the roman numeral
     */
    public static String toRomanNumerals(int level) {
        if (level <= 0) {
            return "I";
        }
        if (level == 1) {
            return "I";
        }
        if (level == 2) {
            return "II";
        }
        if (level == 3) {
            return "III";
        }
        if (level == 4) {
            return "IV";
        }
        if (level == 5) {
            return "V";
        }
        if (level == 6) {
            return "VI";
        }
        if (level == 7) {
            return "VII";
        }
        if (level == 8) {
            return "VIII";
        }
        if (level == 9) {
            return "IX";
        }
        if (level == 10) {
            return "X";
        }
        return String.valueOf(level);
    }

    public ItemStack applyEnchantment(ItemStack itemStack, String iridiumEnchant, CustomEnchant customEnchant, int level) {
        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound nbtCompound = nbtItem.getOrCreateCompound("iridiumenchants");
        int currentLevel = nbtCompound.hasKey(iridiumEnchant) ? nbtCompound.getInteger(iridiumEnchant) : 0;
        nbtCompound.setInteger(iridiumEnchant, level);
        ItemStack item = nbtItem.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore = lore.stream().filter(s ->
                !ChatColor.stripColor(s).equalsIgnoreCase(iridiumEnchant + " " + toRomanNumerals(currentLevel))
        ).collect(Collectors.toList());
        lore.add(StringUtils.color(customEnchant.getDisplayName() + " " + toRomanNumerals(level)));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Get an enchantment from an enchantment crystal
     *
     * @param itemStack The crystal itemstack
     * @return an IridiumEnchant
     */
    public Optional<String> getEnchantmentFromCrystal(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("iridiumenchants.enchantment")) {
            return Optional.empty();
        }
        return Optional.of(nbtItem.getString("iridiumenchants.enchantment"));
    }

    /**
     * Get an enchantment level from a crystal
     *
     * @param itemStack The crystal itemstack
     * @return The enchantment level;
     */
    public int getEnchantmentLevelFromCrystal(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("iridiumenchants.level")) {
            return 0;
        }
        return nbtItem.getInteger("iridiumenchants.level");
    }

    /**
     * Get an Enchantment Crystal from enchant key
     *
     * @param iridiumEnchant The enchantment
     * @param level          The level of the enchant
     * @return The Enchantment Crystal ItemStack
     */
    public ItemStack getEnchantmentCrystal(String iridiumEnchant, CustomEnchant customEnchant, int level) {
        Item item = IridiumEnchants.getInstance().getConfiguration().enchantmentCrystal;
        NBTItem nbtItem = new NBTItem(ItemStackUtils.makeItem(item, Arrays.asList(
                new Placeholder("enchant", WordUtils.capitalize(iridiumEnchant) + " " + toRomanNumerals(level)),
                new Placeholder("type", WordUtils.capitalize(customEnchant.getType().name().toLowerCase())),
                new Placeholder("description", customEnchant.getDescription())
        )));
        nbtItem.setString("iridiumenchants.enchantment", iridiumEnchant);
        nbtItem.setInteger("iridiumenchants.level", level);
        return nbtItem.getItem();
    }

}
