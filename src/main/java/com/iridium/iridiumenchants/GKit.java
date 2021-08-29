package com.iridium.iridiumenchants;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class GKit {
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
