package com.iridium.iridiumenchants.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.*;
import com.iridium.iridiumenchants.configs.inventories.AnimatedBackgroundGUI;
import com.iridium.iridiumskyblock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EnchantmentTierGUI implements GUI {

    private int frame = 0;
    private int tickCount = 0;

    @NotNull
    @Override
    public Inventory getInventory() {
        AnimatedBackgroundGUI animatedBackgroundGUI = IridiumEnchants.getInstance().getInventories().enchantsTierGUI;
        Inventory inventory = Bukkit.createInventory(this, animatedBackgroundGUI.size, StringUtils.color(animatedBackgroundGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        Background background = IridiumEnchants.getInstance().getInventories().enchantsTierGUI.background.backgroundFrames.get(frame);
        if (background != null) {
            InventoryUtils.fillInventory(inventory, background);
        }

        for (Tier tier : IridiumEnchants.getInstance().getConfiguration().tiers.values()) {
            inventory.setItem(tier.item.slot, ItemStackUtils.makeItem(tier.item, Collections.singletonList(
                    new Placeholder("cost", String.valueOf(tier.experienceCost))
            )));
        }

        tickCount++;
        if (tickCount % IridiumEnchants.getInstance().getInventories().enchantsTierGUI.nextFrameInterval == 0) {
            frame++;
            if (IridiumEnchants.getInstance().getInventories().enchantsTierGUI.background.backgroundFrames.get(frame) == null) {
                frame = 0;
            }
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        IridiumEnchants.getInstance().getConfiguration().tiers.entrySet().stream().filter(tier -> tier.getValue().item.slot == event.getSlot()).findFirst().ifPresent(tier -> {
            if (event.getClick() == ClickType.RIGHT) {
                event.getWhoClicked().openInventory(new EnchantmentTierListGUI(1, tier.getKey()).getInventory());
                return;
            }
            if (!purchase(tier.getValue(), player)) {
                player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().notEnoughExperience
                        .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                        .replace("%tier%", tier.getKey())
                ));
                return;
            }
            List<String[]> customEnchants = new ArrayList<>();
            for (Map.Entry<String, CustomEnchant> customEnchant : IridiumEnchants.getInstance().getCustomEnchants().customEnchants.entrySet()) {
                for (Map.Entry<Integer, Level> level : customEnchant.getValue().levels.entrySet()) {
                    if (level.getValue().tiers.contains(tier.getKey())) {
                        customEnchants.add(new String[]{"give", player.getName(), customEnchant.getKey(), String.valueOf(level.getKey())});
                    }
                }
            }
            Random random = new Random();
            String[] args = customEnchants.get(random.nextInt(customEnchants.size()));
            IridiumEnchants.getInstance().getCommands().giveCommand.execute(Bukkit.getConsoleSender(), args);
            player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().gotEnchantmentFromTier
                    .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                    .replace("%tier%", tier.getKey())
                    .replace("%enchant%", args[2])
                    .replace("%level%", IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(Integer.parseInt(args[3])))
            ));
            IridiumEnchants.getInstance().getConfiguration().tierPurchaseSound.play(player);
        });
    }

    public boolean purchase(Tier tier, Player player) {
        if (tier.experienceType == ExperienceType.LEVEL) {
            if (player.getLevel() < tier.experienceCost) {
                return false;
            } else {
                player.setLevel(player.getLevel() - tier.experienceCost);
                return true;
            }
        } else {
            int playerExperience = PlayerUtils.getTotalExperience(player);
            if (playerExperience < tier.experienceCost) {
                return false;
            } else {
                PlayerUtils.setTotalExperience(player, playerExperience - tier.experienceCost);
                return true;
            }
        }
    }
}
