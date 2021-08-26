package com.iridium.iridiumenchants.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;

import java.util.Arrays;
import java.util.Map;

public class Configuration {
    public String prefix = "&e&lIridiumEnchants &8»";
    public Item enchantmentCrystal = new Item(XMaterial.NETHER_STAR, 1, "&e&l%enchant%", Arrays.asList("&7Type: %type%", "&7Description: %description%"));
    public Map<XMaterial, XMaterial> smelt = ImmutableMap.<XMaterial, XMaterial>builder()
            .put(XMaterial.IRON_ORE, XMaterial.IRON_INGOT)
            .put(XMaterial.DEEPSLATE_IRON_ORE, XMaterial.IRON_INGOT)
            .put(XMaterial.RAW_IRON, XMaterial.IRON_INGOT)
            .put(XMaterial.RAW_GOLD, XMaterial.GOLD_INGOT)
            .put(XMaterial.RAW_COPPER, XMaterial.COPPER_INGOT)
            .put(XMaterial.GOLD_ORE, XMaterial.GOLD_INGOT)
            .put(XMaterial.DEEPSLATE_GOLD_ORE, XMaterial.GOLD_INGOT)
            .put(XMaterial.SAND, XMaterial.GLASS)
            .build();
}
