package com.iridium.iridiumenchants.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumenchants.Tier;

import java.util.Arrays;
import java.util.List;
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
    public List<XMaterial> infusionBlacklist = Arrays.asList(XMaterial.BEDROCK, XMaterial.SPAWNER, XMaterial.CHEST, XMaterial.TRAPPED_CHEST);
    public Map<String, Tier> tiers = ImmutableMap.<String, Tier>builder()
            .put("Common", new Tier(new Item(XMaterial.ENCHANTED_BOOK, 11, 1, "&b&lCOMMON ENCHANTMENT", Arrays.asList("&7Click to purchase a random common enchantment", "", "&e&lCOST: &7%cost%")), 20))
            .put("Elite", new Tier(new Item(XMaterial.ENCHANTED_BOOK, 13, 1, "&e&lELITE ENCHANTMENT", Arrays.asList("&7Click to purchase a random elite enchantment", "", "&e&lCOST: &7%cost%")), 40))
            .put("Legendary", new Tier(new Item(XMaterial.ENCHANTED_BOOK, 15, 1, "&6&lLEGENDARY ENCHANTMENT", Arrays.asList("&7Click to purchase a random common enchantment", "", "&e&lCOST: &7%cost%")), 60))
            .build();
}
