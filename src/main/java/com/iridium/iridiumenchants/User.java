package com.iridium.iridiumenchants;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.util.*;

public class User {
    private final UUID uuid;
    private final HashMap<String, LocalDateTime> gkitsCooldown;
    @Getter
    private final BukkitTask bukkitTask;
    // the cycle number the passive task is on
    private int tickCycle = 0;
    private List<UserHealthKey> userHealthKeys = new ArrayList<>();

    public User(UUID uuid) {
        this.uuid = uuid;
        this.gkitsCooldown = new HashMap<>();
        bukkitTask = Bukkit.getScheduler().runTaskTimer(IridiumEnchants.getInstance(), this::passive, 0, 0);
    }

    public void applyCooldown(String gkit, int seconds) {
        gkitsCooldown.put(gkit, LocalDateTime.now().plusSeconds(seconds));
    }

    public LocalDateTime getCooldown(String gkit) {
        return gkitsCooldown.getOrDefault(gkit, LocalDateTime.now());
    }

    public void setUserHealthKey(ItemStack itemStack, int extraHealth, int ticks) {
        Optional<UserHealthKey> optional = userHealthKeys.stream().filter(userHealthKey1 -> userHealthKey1.getKey().equals(itemStack)).findAny();
        if (optional.isPresent()) {
            optional.get().setExtraHealth(extraHealth);
            optional.get().setTicksRemaining(ticks);
        } else {
            userHealthKeys.add(new UserHealthKey(itemStack, extraHealth, ticks));
        }
    }

    private void passive() {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            double maxHealth = 20;
            for (UserHealthKey userHealthKey : userHealthKeys) {
                maxHealth += userHealthKey.getExtraHealth();
                userHealthKey.tick();
            }
            if (player.getMaxHealth() != maxHealth) {
                player.setHealth(Math.min(maxHealth, player.getHealth()));
                player.setMaxHealth(maxHealth);
            }
            userHealthKeys.removeIf(userHealthKey -> userHealthKey.getTicksRemaining() < 1);

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
