package com.iridium.iridiumenchants.managers;

import com.cryptomorin.xseries.XEnchantment;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.GKit;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.User;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GkitsManager {

    private final Map<UUID, Object> locks = new ConcurrentHashMap<>();

    public void giveUserGkit(HumanEntity player, String gkitName, GKit gKit) {
        Bukkit.getScheduler().runTaskAsynchronously(IridiumEnchants.getInstance(), () -> {
            User user = IridiumEnchants.getInstance().getUserManager().getUser(player);
            Object lock = locks.computeIfAbsent(player.getUniqueId(), u -> new Object());

            synchronized (lock) {
                LocalDateTime cooldown = IridiumEnchants.getInstance().getCooldownManager().getCooldown(user.getUuid(), gkitName).join();
                if (LocalDateTime.now().until(cooldown, ChronoUnit.SECONDS) > 0) {
                    long days = LocalDateTime.now().until(cooldown, ChronoUnit.DAYS);
                    long hours = LocalDateTime.now().until(cooldown, ChronoUnit.HOURS) - (days * 24);
                    long minutes = LocalDateTime.now().until(cooldown, ChronoUnit.MINUTES) - ((hours + (days * 24)) * 60);
                    long seconds = LocalDateTime.now().until(cooldown, ChronoUnit.SECONDS) - ((minutes + (hours + (days * 24)) * 60) * 60);
                    player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().gkitOnCooldown
                            .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                            .replace("%gkit%", gkitName)
                            .replace("%days%", String.valueOf((int) Math.max(days, 0)))
                            .replace("%hours%", String.valueOf((int) Math.max(hours, 0)))
                            .replace("%minutes%", String.valueOf((int) Math.max(minutes, 0)))
                            .replace("%seconds%", String.valueOf((int) Math.max(seconds, 0)))
                    ));
                    return;
                }
                IridiumEnchants.getInstance().getCooldownManager().applyCooldown(user.getUuid(), gkitName, LocalDateTime.now().plusSeconds(gKit.cooldown)).join();
                IridiumEnchants.getInstance().getGkitsManager().getItemsFromGkit(gKit).forEach(itemStack -> player.getInventory().addItem(itemStack).values().forEach(item -> player.getWorld().dropItem(player.getLocation(), item)));
            }
        });
    }

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
