package com.iridium.iridiumenchants.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.Level;
import com.iridium.iridiumenchants.Type;

import java.util.Collections;
import java.util.Map;

public class CustomEnchants {
    public Map<String, CustomEnchant> customEnchants = ImmutableMap.<String, CustomEnchant>builder()
            .put("Speed", new CustomEnchant("&7Speed", "Gives you an unlimited Speed effect", Type.BOOTS, "PASSIVE:20", ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:SPEED:1:5")))
                    .put(2, new Level(100, Collections.singletonList("POTION:SPEED:2:5")))
                    .put(3, new Level(100, Collections.singletonList("POTION:SPEED:3:5")))
                    .build(), true))
            .put("Jump", new CustomEnchant("&7Jump", "Gives you an unlimited Jump Boost effect", Type.BOOTS, "PASSIVE:20", ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:JUMP:1:5")))
                    .put(2, new Level(100, Collections.singletonList("POTION:JUMP:2:5")))
                    .put(3, new Level(100, Collections.singletonList("POTION:JUMP:3:5")))
                    .build(), true))
            .put("Visionary", new CustomEnchant("&7Visionary", "Gives you an unlimited Night Vision effect", Type.HELMET, "PASSIVE:20", ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:NIGHT_VISION:1:5")))
                    .build(), true))
            .put("Netherskin", new CustomEnchant("&7Netherskin", "Gives you an unlimited Fire Resistance effect", Type.CHESTPLATE, "PASSIVE:20", ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:FIRE_RESISTANCE:1:5")))
                    .build(), true))
            .put("Aquatic", new CustomEnchant("&7Aquatic", "Gives you an unlimited Water Breathing effect", Type.HELMET, "PASSIVE:20", ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:WATER_BREATHING:1:5")))
                    .build(), true))
            .put("Haste", new CustomEnchant("&7Haste", "Gives you an unlimited Haste effect", Type.PICKAXE, "PASSIVE:20", ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:FAST_DIGGING:1:5")))
                    .put(2, new Level(100, Collections.singletonList("POTION:FAST_DIGGING:2:5")))
                    .put(3, new Level(100, Collections.singletonList("POTION:FAST_DIGGING:3:5")))
                    .build(), true))
            .put("Strength", new CustomEnchant("&7Strength", "Gives you an unlimited Strength effect", Type.SWORD, "PASSIVE:20", ImmutableMap.<Integer, Level>builder()
                    .put(1, new Level(100, Collections.singletonList("POTION:INCREASE_DAMAGE:1:5")))
                    .put(2, new Level(100, Collections.singletonList("POTION:INCREASE_DAMAGE:2:5")))
                    .put(3, new Level(100, Collections.singletonList("POTION:INCREASE_DAMAGE:3:5")))
                    .build(), true))
            .build();
}
