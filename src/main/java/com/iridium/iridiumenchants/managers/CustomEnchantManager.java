package com.iridium.iridiumenchants.managers;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.*;
import com.iridium.iridiumenchants.conditions.Condition;
import com.iridium.iridiumenchants.effects.Effect;
import com.iridium.iridiumenchants.utils.EnchantmentUtils;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class CustomEnchantManager {

    /**
     * Converts the level to roman numerals
     *
     * @param level the level
     * @return the roman numeral
     */
    public String toRomanNumerals(int level) {
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
        NBTCompound nbtCompound = nbtItem.getOrCreateCompound("iridiumenchants").getOrCreateCompound("enchants");
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
        item.setItemMeta(EnchantmentUtils.addEnchantmentEffect(itemMeta, itemStack.getType()));
        return item;
    }

    /**
     * Get an enchantment from an enchantment crystal
     *
     * @param itemStack The crystal itemstack
     * @return an IridiumEnchant
     */
    public Optional<String> getEnchantmentFromCrystal(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return Optional.empty();
        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound nbtCompound = nbtItem.getOrCreateCompound("iridiumenchants");
        if (!nbtCompound.hasKey("enchantment")) {
            return Optional.empty();
        }
        return Optional.of(nbtCompound.getString("enchantment"));
    }

    /**
     * Get an enchantment level from a crystal
     *
     * @param itemStack The crystal itemstack
     * @return The enchantment level;
     */
    public int getEnchantmentLevelFromCrystal(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return 0;
        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound nbtCompound = nbtItem.getOrCreateCompound("iridiumenchants");
        if (!nbtCompound.hasKey("level")) {
            return 0;
        }
        return nbtCompound.getInteger("level");
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
                new Placeholder("type", WordUtils.capitalize(customEnchant.getType())),
                new Placeholder("description", customEnchant.getDescription())
        )));
        NBTCompound nbtCompound = nbtItem.getOrCreateCompound("iridiumenchants");
        nbtCompound.setString("enchantment", iridiumEnchant);
        nbtCompound.setInteger("level", level);
        return nbtItem.getItem();
    }

    /**
     * Gets all enchantments from an item
     *
     * @param itemStack the item
     * @return all enchantments from this item
     */
    public Map<String, Integer> getEnchantmentsFromItem(ItemStack itemStack) {
        Map<String, Integer> hashMap = new HashMap<>();
        if (itemStack == null || itemStack.getType() == Material.AIR) return hashMap;
        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound nbtCompound = nbtItem.getOrCreateCompound("iridiumenchants").getOrCreateCompound("enchants");
        for (String key : nbtCompound.getKeys()) {
            hashMap.put(key, nbtCompound.getInteger(key));
        }
        return hashMap;
    }

    public boolean canApply(ItemStack itemStack, String iridiumEnchant, int level) {
        Map<String, Integer> enchantments = getEnchantmentsFromItem(itemStack);
        return !enchantments.containsKey(iridiumEnchant) || enchantments.get(iridiumEnchant) < level;
    }

    public boolean canApply(ItemStack itemStack, String iridiumEnchant, int level, Type type) {
        return canApply(itemStack, iridiumEnchant, level) && type.includes(itemStack.getType());
    }

    /**
     * Applies all effects from an ItemStack with a given trigger
     *
     * @param itemStack The specified ItemStack
     * @param trigger   the specified Trigger
     * @param player    the specified Player
     * @param target    The specified target
     */
    public void applyEffectsFromItem(ItemStack itemStack, Trigger trigger, LivingEntity player, LivingEntity target, Event event) {
        Map<String, Integer> enchants = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentsFromItem(itemStack);
        for (Map.Entry<String, Integer> enchant : enchants.entrySet()) {
            CustomEnchant customEnchant = IridiumEnchants.getInstance().getCustomEnchantments().get(enchant.getKey());
            if (customEnchant == null) continue;
            if (!trigger.isTrigger(customEnchant.trigger)) continue;
            Level level = customEnchant.levels.get(enchant.getValue());
            if (level == null) continue;
            double random = Math.random() * 100;
            if (random > level.chance) continue;
            boolean canApplyEffects = true;
            for (String conditions : level.conditions) {
                String[] conditionArgs = conditions.toUpperCase().split(" ");
                if (conditionArgs.length == 0) continue;
                Condition condition = IridiumEnchants.getInstance().getConditions().get(conditionArgs[0]);
                if (condition != null) {
                    if (!condition.apply(player, target, conditionArgs, itemStack)) canApplyEffects = false;
                }
            }
            if (canApplyEffects) {
                for (String effects : level.effects) {
                    String[] effectArgs = effects.toUpperCase().split(":");
                    if (effectArgs.length == 0) continue;
                    Effect effect = IridiumEnchants.getInstance().getEffects().get(effectArgs[0]);
                    if (effect != null) {
                        effect.apply(player, target, effectArgs, itemStack, event);
                    }
                }
            }
        }
    }

}
