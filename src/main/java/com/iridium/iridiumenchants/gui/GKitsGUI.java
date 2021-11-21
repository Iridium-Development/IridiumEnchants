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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;

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

    @Override
    public void addContent(Inventory inventory) {
        Background background = IridiumEnchants.getInstance().getInventories().gkitsGUI.background.backgroundFrames.get(frame);
        if (background != null) {
            InventoryUtils.fillInventory(inventory, background);
        }

        for (Map.Entry<String, GKit> gkits : IridiumEnchants.getInstance().getGKits().gkits.entrySet()) {
            LocalDateTime cooldown = user.getCooldown(gkits.getKey());
            long days = LocalDateTime.now().until(cooldown, ChronoUnit.DAYS);
            long hours = LocalDateTime.now().until(cooldown, ChronoUnit.HOURS) - (days * 24);
            long minutes = LocalDateTime.now().until(cooldown, ChronoUnit.MINUTES) - ((hours + (days * 24)) * 60);
            long seconds = LocalDateTime.now().until(cooldown, ChronoUnit.SECONDS) - ((minutes + (hours + (days * 24)) * 60) * 60);
            Item item = gkits.getValue().guiItem;
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
                    LocalDateTime cooldown = user.getCooldown(gkit.getKey());
                    if (LocalDateTime.now().until(cooldown, ChronoUnit.SECONDS) > 0) {
                        long days = LocalDateTime.now().until(cooldown, ChronoUnit.DAYS);
                        long hours = LocalDateTime.now().until(cooldown, ChronoUnit.HOURS) - (days * 24);
                        long minutes = LocalDateTime.now().until(cooldown, ChronoUnit.MINUTES) - ((hours + (days * 24)) * 60);
                        long seconds = LocalDateTime.now().until(cooldown, ChronoUnit.SECONDS) - ((minutes + (hours + (days * 24)) * 60) * 60);
                        event.getWhoClicked().sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().gkitOnCooldown
                                .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                                .replace("%gkit%", gkit.getKey())
                                .replace("%days%", String.valueOf((int) Math.max(days, 0)))
                                .replace("%hours%", String.valueOf((int) Math.max(hours, 0)))
                                .replace("%minutes%", String.valueOf((int) Math.max(minutes, 0)))
                                .replace("%seconds%", String.valueOf((int) Math.max(seconds, 0)))
                        ));
                        return;
                    }
                    IridiumEnchants.getInstance().getUserManager().getUser(event.getWhoClicked()).applyCooldown(gkit.getKey(), gkit.getValue().cooldown);
                    IridiumEnchants.getInstance().getGkitsManager().getItemsFromGkit(gkit.getValue()).forEach(itemStack ->
                            player.getInventory().addItem(itemStack).values().forEach(item ->
                                    player.getWorld().dropItem(player.getLocation(), item)));
                }
            }
        }
    }
}
