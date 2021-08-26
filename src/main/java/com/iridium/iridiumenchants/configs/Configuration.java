package com.iridium.iridiumenchants.configs;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;

import java.util.Arrays;

public class Configuration {
    public String prefix = "&e&lIridiumEnchants &8»";
    public Item enchantmentCrystal = new Item(XMaterial.NETHER_STAR, 1, "&e&l%enchant%", Arrays.asList("&7Type: %type%", "&7Description: %description%"));
}
