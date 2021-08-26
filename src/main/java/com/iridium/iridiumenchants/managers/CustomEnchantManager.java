package com.iridium.iridiumenchants.managers;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.nbtapi.NBTItem;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class CustomEnchantManager {

    private Map<String, IridiumEnchant> customEnchants;

    /**
     * Register CustomEnchants to spigot
     */
    public void registerEnchants() {
        customEnchants = new HashMap<>();

        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, CustomEnchant> entrySet : IridiumEnchants.getInstance().getCustomEnchants().customEnchants.entrySet()) {
            IridiumEnchant iridiumEnchant = new IridiumEnchant(entrySet.getKey().toLowerCase().replace(" ", "_"), entrySet.getValue());
            customEnchants.put(entrySet.getKey().toLowerCase().replace(" ", "_"), iridiumEnchant);
            if (Arrays.stream(Enchantment.values()).anyMatch(enchantment -> enchantment.getKey().equals(iridiumEnchant.getKey()))) {
                Enchantment.registerEnchantment(iridiumEnchant);
            }
        }
    }

    /**
     * Get an IridiumEnchant from a key
     *
     * @param string The enchantment key
     * @return The IridiumEnchant
     */
    public Optional<IridiumEnchant> getEnchantment(String string) {
        return Optional.ofNullable(customEnchants.get(string.toLowerCase().replace(" ", "_")));
    }

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

    public void applyEnchantment(ItemStack itemStack, IridiumEnchant iridiumEnchant, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();
        int existingLevel = itemMeta.getEnchantLevel(iridiumEnchant);
        if (existingLevel != 0) {
            lore = lore.stream().filter(s ->
                    ChatColor.stripColor(s).equalsIgnoreCase(iridiumEnchant.getCustomEnchant().getDisplayName() + " " + toRomanNumerals(level))
            ).collect(Collectors.toList());
        }
        lore.add(StringUtils.color(iridiumEnchant.getCustomEnchant().getDisplayName() + " " + toRomanNumerals(level)));
        itemMeta.setLore(lore);
        itemMeta.addEnchant(iridiumEnchant, level, true);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Get an enchantment from an enchantment crystal
     *
     * @param itemStack The crystal itemstack
     * @return an IridiumEnchant
     */
    public Optional<IridiumEnchant> getEnchantmentFromCrystal(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("iridiumenchants.enchantment")) {
            return Optional.empty();
        }
        return getEnchantment(nbtItem.getString("iridiumenchants.enchantment"));
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
    public ItemStack getEnchantmentCrystal(IridiumEnchant iridiumEnchant, int level) {
        Item item = IridiumEnchants.getInstance().getConfiguration().enchantmentCrystal;
        NBTItem nbtItem = new NBTItem(ItemStackUtils.makeItem(item, Arrays.asList(
                new Placeholder("enchant", WordUtils.capitalize(iridiumEnchant.getKey().toString().replace("minecraft:", "").replace("_", " ")) + " " + toRomanNumerals(level)),
                new Placeholder("type", WordUtils.capitalize(iridiumEnchant.getCustomEnchant().getType().name().toLowerCase())),
                new Placeholder("description", iridiumEnchant.getCustomEnchant().getDescription())
        )));
        nbtItem.setString("iridiumenchants.enchantment", iridiumEnchant.getKey().toString().replace("minecraft:", ""));
        nbtItem.setInteger("iridiumenchants.level", level);
        return nbtItem.getItem();
    }

}
