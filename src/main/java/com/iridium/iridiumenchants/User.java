package com.iridium.iridiumenchants;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
                IridiumEnchants.getInstance().getCustomEnchantManager().applyEffectsFromItem(itemStack, trigger -> {
                    String[] args = trigger.toUpperCase().split(":");
                    if (!args[0].equals("PASSIVE")) return false;
                    int cycleTime;
                    try {
                        cycleTime = args.length > 1 ? Integer.parseInt(args[1]) : 1;
                    } catch (NumberFormatException exception) {
                        cycleTime = 1;
                    }
                    return tickCycle % cycleTime == 0;
                }, player, player, null);
            }
        }
        tickCycle++;
    }
}
