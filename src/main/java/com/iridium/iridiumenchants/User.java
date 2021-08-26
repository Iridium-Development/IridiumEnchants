package com.iridium.iridiumenchants;

import com.iridium.iridiumenchants.effects.Effect;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User {
    private final UUID uuid;
    @Getter
    private final BukkitTask bukkitTask;
    // the cycle number the passive task is on
    private int tickCycle = 0;

    public User(UUID uuid) {
        this.uuid = uuid;
        bukkitTask = Bukkit.getScheduler().runTaskTimer(IridiumEnchants.getInstance(), this::passive, 0, 0);
    }

    private void passive() {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            List<ItemStack> itemStackList = Arrays.asList(
                    player.getItemInHand(),
                    player.getInventory().getBoots(),
                    player.getInventory().getLeggings(),
                    player.getInventory().getChestplate(),
                    player.getInventory().getHelmet()
            );
            for (ItemStack itemStack : itemStackList) {
                Map<String, Integer> enchants = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentsFromItem(itemStack);
                for (Map.Entry<String, Integer> enchant : enchants.entrySet()) {
                    CustomEnchant customEnchant = IridiumEnchants.getInstance().getCustomEnchants().customEnchants.get(enchant.getKey());
                    if (customEnchant == null) continue;
                    String[] trigger = customEnchant.trigger.toUpperCase().split(":");
                    if (!trigger[0].equals("PASSIVE")) continue;
                    int cycleTime;
                    try {
                        cycleTime = trigger.length > 1 ? Integer.parseInt(trigger[1]) : 1;
                    } catch (NumberFormatException exception) {
                        cycleTime = 1;
                    }
                    if (tickCycle % cycleTime != 0) continue;
                    Level level = customEnchant.levels.get(enchant.getValue());
                    if (level == null) continue;
                    for (String effects : level.effects) {
                        String[] effectArgs = effects.toUpperCase().split(":");
                        if (effectArgs.length == 0) continue;
                        Effect effect = IridiumEnchants.getInstance().getEffects().get(effectArgs[0]);
                        if (effect != null) {
                            effect.apply(player, player, effectArgs);
                        }
                    }
                }
            }
        }
        tickCycle++;
    }
}
