package com.iridium.iridiumenchants.managers;

import com.cryptomorin.xseries.XEnchantment;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.GKit;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GkitsManager {
    public List<ItemStack> getItemsFromGkit(GKit gKit) {
        return gKit.items.values().stream()
                .map(gKitItem -> {
                    ItemStack itemStack = gKitItem.material.parseItem();
                    if (itemStack == null) return null;
                    itemStack.setAmount(gKitItem.amount);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta == null) return itemStack;
                    if (gKitItem.title != null) itemMeta.setDisplayName(StringUtils.color(gKitItem.title));
                    itemStack.setItemMeta(itemMeta);
                    if (gKitItem.enchantments != null)
                        for (Map.Entry<String, Integer> enchants : gKitItem.enchantments.entrySet()) {
                            Optional<XEnchantment> xEnchantment = XEnchantment.matchXEnchantment(enchants.getKey());
                            if (xEnchantment.isPresent()) {
                                Enchantment enchantment = xEnchantment.get().getEnchant();
                                if (enchantment != null) {
                                    itemStack.addUnsafeEnchantment(enchantment, enchants.getValue());
                                }
                            } else {
                                CustomEnchant customEnchant = IridiumEnchants.getInstance().getCustomEnchantments().get(enchants.getKey());
                                if (customEnchant != null) {
                                    itemStack = IridiumEnchants.getInstance().getCustomEnchantManager().applyEnchantment(itemStack, enchants.getKey(), customEnchant, enchants.getValue());
                                }
                            }
                        }
                    return itemStack;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
