package com.iridium.iridiumenchants.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumenchants.Type;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Types {
    public Map<String, Type> types = ImmutableMap.<String, Type>builder()
            .put("Fishing Rod", new Type(Collections.singletonList("FISHING_ROD")))
            .put("Tool", new Type(Arrays.asList("Pickaxe", "Axe", "Shovel", "Hoe")))
            .put("Armor", new Type(Arrays.asList("Helmet", "Chestplate", "Leggings", "Boots")))
            .put("Bows", new Type(Collections.singletonList("BOW")))
            .put("Sword", new Type(Arrays.asList("WOODEN_SWORD", "STONE_SWORD", "IRON_SWORD", "GOLDEN_SWORD", "DIAMOND_SWORD", "NETHERITE_SWORD")))
            .put("Helmet", new Type(Arrays.asList("LEATHER_HELMET", "CHAINMAIL_HELMET", "IRON_HELMET", "GOLDEN_HELMET", "DIAMOND_HELMET", "NETHERITE_HELMET")))
            .put("Chestplate", new Type(Arrays.asList("LEATHER_CHESTPLATE", "CHAINMAIL_CHESTPLATE", "IRON_CHESTPLATE", "GOLDEN_CHESTPLATE", "DIAMOND_CHESTPLATE", "NETHERITE_CHESTPLATE")))
            .put("Leggings", new Type(Arrays.asList("LEATHER_LEGGINGS", "CHAINMAIL_LEGGINGS", "IRON_LEGGINGS", "GOLDEN_LEGGINGS", "DIAMOND_LEGGINGS", "NETHERITE_LEGGINGS")))
            .put("Boots", new Type(Arrays.asList("LEATHER_BOOTS", "CHAINMAIL_BOOTS", "IRON_BOOTS", "GOLDEN_BOOTS", "DIAMOND_BOOTS", "NETHERITE_BOOTS")))
            .put("Pickaxe", new Type(Arrays.asList("WOODEN_PICKAXE", "STONE_PICKAXE", "IRON_PICKAXE", "GOLDEN_PICKAXE", "DIAMOND_PICKAXE", "NETHERITE_PICKAXE")))
            .put("Axe", new Type(Arrays.asList("WOODEN_AXE", "STONE_AXE", "IRON_AXE", "GOLDEN_AXE", "DIAMOND_AXE", "NETHERITE_AXE")))
            .put("Shovel", new Type(Arrays.asList("WOODEN_SHOVEL", "STONE_SHOVEL", "IRON_SHOVEL", "GOLDEN_SHOVEL", "DIAMOND_SHOVEL", "NETHERITE_SHOVEL")))
            .put("Hoe", new Type(Arrays.asList("WOODEN_HOE", "STONE_HOE", "IRON_HOE", "GOLDEN_HOE", "DIAMOND_HOE", "NETHERITE_HOE")))
            .build();
}
