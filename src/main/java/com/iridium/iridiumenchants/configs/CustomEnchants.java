package com.iridium.iridiumenchants.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.Level;
import com.iridium.iridiumenchants.Trigger;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.Collections;
import java.util.Map;

public class CustomEnchants {
    public Map<String, CustomEnchant> customEnchants = ImmutableMap.<String, CustomEnchant>builder()
            .put("Speed", new CustomEnchant("Speed", "Gives you an unlimited Speed effect", EnchantmentTarget.ARMOR_FEET, Trigger.PASSIVE, ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:SPEED:1")))
                    .put(2, new Level(100, Collections.singletonList("POTION:SPEED:2")))
                    .put(3, new Level(100, Collections.singletonList("POTION:SPEED:3")))
                    .build(), true))
            .build();
}
