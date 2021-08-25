package com.iridium.iridiumenchants.managers;

import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomEnchantManager {

    private Map<String, IridiumEnchant> customEnchants;

    public void registerEnchants() {
        customEnchants = new HashMap<>();
        setAcceptingNew(true);
        for (Map.Entry<String, CustomEnchant> entrySet : IridiumEnchants.getInstance().getCustomEnchants().customEnchants.entrySet()) {
            IridiumEnchant iridiumEnchant = new IridiumEnchant(entrySet.getKey(), entrySet.getValue());
            customEnchants.put(entrySet.getKey(), iridiumEnchant);
            if (Arrays.stream(Enchantment.values()).anyMatch(enchantment -> enchantment.getKey().equals(iridiumEnchant.getKey()))) {
                Enchantment.registerEnchantment(iridiumEnchant);
            }
        }
    }

    public IridiumEnchant getEnchantment(String string) {
        return customEnchants.get(string);
    }

    private void setAcceptingNew(boolean acceptingNew) {
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, acceptingNew);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
