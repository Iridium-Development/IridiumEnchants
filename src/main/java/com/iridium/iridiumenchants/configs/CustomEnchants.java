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
            .build();
}
