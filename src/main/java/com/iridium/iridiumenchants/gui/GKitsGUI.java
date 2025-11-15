package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.GKit;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.User;
import com.iridium.iridiumenchants.configs.inventories.AnimatedBackgroundGUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GKitsGUI implements GUI {

    private int frame = 0;
    private int tickCount = 0;
    private final User user;

    public GKitsGUI(User user) {
        this.user = user;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        AnimatedBackgroundGUI animatedBackgroundGUI = IridiumEnchants.getInstance().getInventories().gkitsGUI;
        Inventory inventory = Bukkit.createInventory(this, animatedBackgroundGUI.size, StringUtils.color(animatedBackgroundGUI.title));
        addContent(inventory);
        return inventory;
    }

    private CompletableFuture<List<GkitCooldownData>> GetGkitsCooldowns() {
        return CompletableFuture.supplyAsync(() -> {
            List<GkitCooldownData> gkitCooldownData = new ArrayList<>();
            for (Map.Entry<String, GKit> gkits : IridiumEnchants.getInstance().getGKits().gkits.entrySet()) {
                LocalDateTime cooldown = IridiumEnchants.getInstance().getCooldownManager().getCooldown(user.getUuid(), gkits.getKey()).join();
                gkitCooldownData.add(new GkitCooldownData(gkits.getValue(), cooldown));
            }
            return gkitCooldownData;
        });
    }

    @Override
    public void addContent(Inventory inventory) {
        GetGkitsCooldowns().thenAccept(gkitCooldownData -> Bukkit.getScheduler().runTask(IridiumEnchants.getInstance(), () -> {
            Background background = IridiumEnchants.getInstance().getInventories().gkitsGUI.background.backgroundFrames.get(frame);
            if (background != null) {
                InventoryUtils.fillInventory(inventory, background);
            }

            for (GkitCooldownData cooldownData : gkitCooldownData){
                long days = LocalDateTime.now().until(cooldownData.expirationTime, ChronoUnit.DAYS);
                long hours = LocalDateTime.now().until(cooldownData.expirationTime, ChronoUnit.HOURS) - (days * 24);
                long minutes = LocalDateTime.now().until(cooldownData.expirationTime, ChronoUnit.MINUTES) - ((hours + (days * 24)) * 60);
                long seconds = LocalDateTime.now().until(cooldownData.expirationTime, ChronoUnit.SECONDS) - ((minutes + (hours + (days * 24)) * 60) * 60);
                Item item = cooldownData.gkit.guiItem;
                inventory.setItem(item.slot, ItemStackUtils.makeItem(item, Arrays.asList(
                        new Placeholder("days", String.valueOf((int) Math.max(days, 0))),
                        new Placeholder("hours", String.valueOf((int) Math.max(hours, 0))),
                        new Placeholder("minutes", String.valueOf((int) Math.max(minutes, 0))),
                        new Placeholder("seconds", String.valueOf((int) Math.max(seconds, 0)))
                )));
            }

            tickCount++;
            if (tickCount % IridiumEnchants.getInstance().getInventories().gkitsGUI.nextFrameInterval == 0) {
                frame++;
                if (IridiumEnchants.getInstance().getInventories().gkitsGUI.background.backgroundFrames.get(frame) == null) {
                    frame = 0;
                }
            }

        }));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        for (Map.Entry<String, GKit> gkit : IridiumEnchants.getInstance().getGKits().gkits.entrySet()) {
            if (gkit.getValue().guiItem.slot == event.getSlot()) {
                if (event.getClick() == ClickType.RIGHT) {
                    event.getWhoClicked().openInventory(new GKitsPreviewGUI(gkit).getInventory());
                } else {
                    if (!player.hasPermission(gkit.getValue().permission) && !gkit.getValue().permission.isEmpty()) {
                        player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().noPermission
                                .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                        ));
                        return;
                    }
                    IridiumEnchants.getInstance().getGkitsManager().giveUserGkit(event.getWhoClicked(), gkit.getKey(), gkit.getValue());
                }
            }
        }
    }

    @AllArgsConstructor
    @Getter
    private class GkitCooldownData {
        GKit gkit;
        LocalDateTime expirationTime;
    }
}
