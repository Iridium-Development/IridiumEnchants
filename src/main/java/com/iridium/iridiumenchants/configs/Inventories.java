package com.iridium.iridiumenchants.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnore;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumenchants.configs.inventories.SingleItemGUI;

import java.util.Arrays;
import java.util.Collections;

public class Inventories {
    @JsonIgnore
    private final Background background = new Background(ImmutableMap.<Integer, Item>builder().build());

    public SingleItemGUI enchantsListGUI = new SingleItemGUI(54, "&7Custom Enchants List", background, new Item(
            XMaterial.NETHER_STAR, 1, "&e&l%enchant_name%",
            Arrays.asList(
                    "&7Type: %enchant_type%",
                    "&7Description: %enchant_description%"
            )));

    public Item nextPage = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&a&lNext Page", Collections.emptyList());
    public Item previousPage = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&c&lPrevious Page", Collections.emptyList());

}
