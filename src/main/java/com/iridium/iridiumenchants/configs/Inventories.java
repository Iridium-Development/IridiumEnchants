package com.iridium.iridiumenchants.configs;

import com.cryptomorin.xseries.XMaterial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumenchants.AnimatedBackground;
import com.iridium.iridiumenchants.configs.inventories.AnimatedBackgroundGUI;
import com.iridium.iridiumenchants.configs.inventories.NoItemGUI;
import com.iridium.iridiumenchants.configs.inventories.SingleItemGUI;

import java.util.Arrays;
import java.util.Collections;

public class Inventories {
    @JsonIgnore
    private final Background background = new Background(ImmutableMap.<Integer, Item>builder().build());
    @JsonIgnore
    private final AnimatedBackground plainAnimatedBackground = new AnimatedBackground(ImmutableMap.<Integer, Background>builder()
            .put(0, background)
            .build());
    @JsonIgnore
    private final AnimatedBackground animatedBackground = new AnimatedBackground(ImmutableMap.<Integer, Background>builder()
            .put(0, new Background(ImmutableMap.<Integer, Item>builder()
                    .put(9, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(10, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(11, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(12, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(13, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(14, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(15, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(16, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(17, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .build(), new Item(XMaterial.PURPLE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())))
            .put(1, new Background(ImmutableMap.<Integer, Item>builder()
                    .put(9, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(10, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(11, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(12, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(13, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(14, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(15, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(16, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(17, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .build(), new Item(XMaterial.PURPLE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())))
            .put(2, new Background(ImmutableMap.<Integer, Item>builder()
                    .put(9, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(10, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(11, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(12, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(13, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(14, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(15, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(16, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(17, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .build(), new Item(XMaterial.CYAN_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())))
            .put(3, new Background(ImmutableMap.<Integer, Item>builder()
                    .put(9, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(10, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(11, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(12, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(13, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(14, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(15, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(16, new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .put(17, new Item(XMaterial.BLUE_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
                    .build(), new Item(XMaterial.CYAN_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())))
            .build());

    public SingleItemGUI enchantsListGUI = new SingleItemGUI(54, "&7Custom Enchants List", background, new Item(
            XMaterial.NETHER_STAR, 1, "&e&l%enchant_name%",
            Arrays.asList(
                    "&7Type: %enchant_type%",
                    "&7Description: %enchant_description%"
            )));

    public SingleItemGUI enchantsTierListGUI = new SingleItemGUI(54, "&7%tier% Custom Enchants List", background, new Item(
            XMaterial.NETHER_STAR, 1, "&e&l%enchant_name% %enchant_level%",
            Arrays.asList(
                    "&7Type: %enchant_type%",
                    "&7Description: %enchant_description%"
            )));
    public String enchantmentSelectGUITitle = "&7IridiumEnchants";

    public AnimatedBackgroundGUI enchantsTierGUI = new AnimatedBackgroundGUI(27, "&7Iridium Enchants", animatedBackground, 5);

    public AnimatedBackgroundGUI gkitsGUI = new AnimatedBackgroundGUI(27, "&7GKits", plainAnimatedBackground, 5);
    public NoItemGUI gkitsPreview = new NoItemGUI(0, "&7%gkit% Gkit Preview", background);

    public Item nextPage = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&a&lNext Page", Collections.emptyList());
    public Item previousPage = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&c&lPrevious Page", Collections.emptyList());

}
