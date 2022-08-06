package com.iridium.iridiumenchants;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnore;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class GKit {
    public long  cooldown;
    public String permission;
    public Item guiItem;
    public Map<Integer, GKitItem> items;

    @JsonIgnore
    public CooldownProvider<Player> cooldownProvider;

    public GKit(Duration cooldown, String permission, Item guiItem, Map<Integer, GKitItem> items){
        this.cooldown = cooldown.getSeconds();
        this.permission = permission;
        this.guiItem = guiItem;
        this.items = items;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class GKitItem {
        public XMaterial material;
        public int amount;
        public String title;
        public Map<String, Integer> enchantments;
    }

    public CooldownProvider<Player> getCooldownProvider() {
        if (cooldownProvider == null) {
            this.cooldownProvider = CooldownProvider.newInstance(Duration.ofSeconds(cooldown));
        }
        return cooldownProvider;
    }

}
