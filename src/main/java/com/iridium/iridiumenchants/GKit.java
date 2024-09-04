package com.iridium.iridiumenchants;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumcore.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class GKit {
    public int cooldown;
    public String permission;
    public Item guiItem;
    public Map<Integer, GKitItem> items;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class GKitItem {
        public XMaterial material;
        public int amount;
        public String title;
        public Map<String, Integer> enchantments;
    }
}
