package com.iridium.iridiumenchants.managers;

import com.iridium.iridiumcore.dependencies.nbtapi.NBTCompound;
import com.iridium.iridiumcore.dependencies.nbtapi.NBTItem;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
            IridiumEnchant iridiumEnchant = new IridiumEnchant(entrySet.getKey(), entrySet.getValue());
            customEnchants.put(entrySet.getKey(), iridiumEnchant);
            if (Arrays.stream(Enchantment.values()).anyMatch(enchantment -> enchantment.getKey().equals(iridiumEnchant.getKey()))) {
                Enchantment.registerEnchantment(iridiumEnchant);
            }
        }
    }

    /**
     * Get an IridiumEnchant from a key
     * @param string The enchantment key
     * @return The IridiumEnchant
     */
    public Optional<IridiumEnchant> getEnchantment(String string) {
        return Optional.ofNullable(customEnchants.get(string));
    }

    /**
     * Get an Enchantment Crystal from enchant key
     * @param key The enchantment key
     * @param level The level of the enchant
     * @return The Enchantment Crystal ItemStack
     */
    public ItemStack getEnchantmentCrystal(String key, int level) {
        NBTItem nbtItem = new NBTItem(new ItemStack(Material.NETHER_STAR));
        nbtItem.setString("iridiumenchants.enchantment", key);
        nbtItem.setInteger("iridiumenchants.level", level);
        return nbtItem.getItem();
    }

}
