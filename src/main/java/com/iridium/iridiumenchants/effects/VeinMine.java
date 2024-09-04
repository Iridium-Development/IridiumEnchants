package com.iridium.iridiumenchants.effects;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchants;

import com.iridium.iridiumenchants.listeners.BlockBreakListener;
import lombok.Setter;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.*;

public class VeinMine implements Effect {

    private int chance;
    private int veinCap;
    @Setter
    private boolean placeDropsAtOrigin;
    @Setter
    private boolean protectTool;
    @Setter
    private boolean allowWrongTool;
    @Setter
    private boolean expPenalty;
    private int expPenaltyAmount = IridiumEnchants.getInstance().getConfiguration().veinMineExpPenaltyAmount;

    private final LinkedHashSet<Block> blocksBuffer = new LinkedHashSet<>(), recent = new LinkedHashSet<>();
    private final List<BlockBreakEvent> events = new ArrayList<>();

    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (!(player instanceof Player) && !(event instanceof BlockBreakEvent)) return;

        // Create blockBreakEvent
        Player realPlayer = (Player) player;
        BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;

        if (events.contains(blockBreakEvent)) {
            events.remove(blockBreakEvent);
            return;
        }

        // Check if we should trigger vein mine at all
        if (!IridiumEnchants.getInstance().getConfiguration().veinMiner.contains(
                XMaterial.matchXMaterial(blockBreakEvent.getBlock().getType()))) return;

        // Create variables for event block
        Block eventBlock = blockBreakEvent.getBlock();
        Location eventBlockLocation = eventBlock.getLocation();

        // Get the current vein mine settings
        setConfigOptions(args);

        // Get the blocks to be mined and check if we're vein mining based on how many are returned
        LinkedHashSet<Block> blocksToMine = allocateBlocks(eventBlock);
        if (blocksToMine.size() < 2) return;

        // Check if we should allow vein mining if the tool will not give drops
        blockBreakEvent.setCancelled(triggerWrongToolProtection(itemStack, realPlayer, eventBlock));
        if (blockBreakEvent.isCancelled()) return;

        // Vein mining
        for (Block block : blocksToMine) {

            // Create new event for each block
            BlockBreakEvent breakEvent = new BlockBreakEvent(block, realPlayer);
            events.add(breakEvent);
            new BlockBreakListener().onBlockBreak(breakEvent);
            if (breakEvent.isCancelled()) continue;

            // Check if we should continue to vein mine based on location permission
            breakEvent.setCancelled(triggerLocationProtection(realPlayer, block.getLocation()));
            if (breakEvent.isCancelled()) return;

            // Check if we should protect the tool from breaking
            breakEvent.setCancelled(triggerToolProtection(itemStack, realPlayer));
            if (breakEvent.isCancelled()) return;

            // Roll the chance of damaging the tool
            Random random = new Random();
            int rolledChance = random.nextInt(101);

            // Check if we need to change the amount of exp the block will drop
            if (expPenalty) breakEvent.setExpToDrop(breakEvent.getExpToDrop() - expPenaltyAmount);

            // Check if we need to relocate the drops
            if (placeDropsAtOrigin) {
                for (ItemStack drop : block.getDrops(itemStack)) {
                    eventBlockLocation.getWorld().dropItemNaturally(eventBlockLocation, drop);
                }
                block.setType(getAir(block));
            } else block.breakNaturally(itemStack);

            // Check if we need to damage the tool
            if (chance <= rolledChance) {
                // Check if we need to break the tool
                if (damageTool(itemStack, realPlayer)) return;
            }
        }
    }

    public Material getAir(Block block) {
        for (BlockFace blockFace : BlockFace.values()) {
            if (blockFace == BlockFace.SELF) continue;
            if (block.getRelative(blockFace).getType() == Material.CAVE_AIR) return Material.CAVE_AIR;
        }
        return Material.AIR;
    }

    public String getToolName(ItemStack itemStack) {
        if (itemStack.getItemMeta().hasDisplayName()) return itemStack.getItemMeta().getDisplayName();
        else return itemStack.getType().toString().toLowerCase();
    }

    public boolean triggerWrongToolProtection(ItemStack itemStack, Player player, Block block) {
        if (block.getDrops(itemStack).isEmpty() && !allowWrongTool) {
            String displayName = getToolName(itemStack);
            player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().veinMineWrongTool
                    .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                    .replace("%tool%", WordUtils.capitalize(displayName.replace("_", " ")))
            ));
            return true;
        }
        return false;
    }

    public boolean triggerToolProtection(ItemStack itemStack, Player player) {
        if (getDamage(itemStack) + 1 == getMaxDamage(itemStack) && protectTool) {
            String displayName = getToolName(itemStack);
            player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().cannotVeinMine
                    .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                    .replace("%tool%", WordUtils.capitalize(displayName.replace("_", " ")))
            ));
            return true;
        }
        return false;
    }

    public boolean triggerLocationProtection(Player player, Location location) {
        if (!IridiumEnchants.getInstance().canBuild(player, location)) {
            player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().noPermission
                    .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return true;
        }
        return false;
    }

    private int getDamage(ItemStack itemStack) {
        if(itemStack.getItemMeta() instanceof Damageable) {
            if(((Damageable) itemStack.getItemMeta()).hasDamage()) return ((Damageable) itemStack.getItemMeta()).getDamage();
        }
        return itemStack.getDurability();
    }

    private int getMaxDamage(ItemStack itemStack) {
        return itemStack.getType().getMaxDurability();
    }

    private boolean damageTool(ItemStack itemStack, Player player) {
        Damageable toolMeta = (Damageable) itemStack.getItemMeta();
        int toolDamage = getDamage(itemStack);
        int maxToolDamage = getMaxDamage(itemStack);

        toolDamage++;
        if (toolDamage == maxToolDamage) {
            PlayerItemBreakEvent itemBreakEvent = new PlayerItemBreakEvent(player, itemStack);
            itemBreakEvent.getBrokenItem().setAmount(1);
            return true;
        }

        toolMeta.setDamage(toolDamage);
        itemStack.setItemMeta(toolMeta);

        return false;
    }

    public LinkedHashSet<Block> allocateBlocks(Block block) {
        LinkedHashSet<Block> blocks = new LinkedHashSet<>();
        blocks.add(block);
        this.recent.add(block);

        while (blocksBuffer.size() < veinCap) {
            recentSearch:
            for (Block current : recent) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {

                            if (x == 0 && y == 0 && z == 0) continue;

                            Block relative = current.getRelative(x, y, z);
                            if (blocks.contains(relative) || blocksBuffer.contains(relative)) {
                                continue;
                            }

                            if (relative.getType() != current.getType()) {
                                continue;
                            }

                            if (blocks.size() + blocksBuffer.size() >= veinCap) {
                                break recentSearch;
                            }

                            this.blocksBuffer.add(relative);
                        }
                    }
                }
            }

            if (blocksBuffer.isEmpty()) {
                break;
            }

            this.recent.clear();
            this.recent.addAll(blocksBuffer);
            blocks.addAll(blocksBuffer);

            this.blocksBuffer.clear();
        }

        this.recent.clear();
        return blocks;
    }

    private void setChance(String arg) {
        try {
            chance = Integer.parseInt(arg);
        } catch(NumberFormatException e) {
            chance = 0;
        }
    }

    private void setVeinCap(String arg) {
        try {
            veinCap = Integer.parseInt(arg);
        } catch(NumberFormatException e) {
            veinCap = 25;
        }
    }

    public void setConfigOptions(String[] args) {

        switch(args.length) {
            case 0: {}
            case 1: {
                setChance("");
                setVeinCap("");
                setPlaceDropsAtOrigin(false);
                setProtectTool(false);
                setAllowWrongTool(false);
                setExpPenalty(false);
                break;
            }
            case 2: {
                setChance(args[1]);
                setVeinCap("");
                setPlaceDropsAtOrigin(false);
                setProtectTool(false);
                setAllowWrongTool(false);
                setExpPenalty(false);
                break;
            }
            case 3: {
                setChance(args[1]);
                setVeinCap(args[2]);
                setPlaceDropsAtOrigin(false);
                setProtectTool(false);
                setAllowWrongTool(false);
                setExpPenalty(false);
                break;
            }
            case 4: {
                setChance(args[1]);
                setVeinCap(args[2]);
                setPlaceDropsAtOrigin(Boolean.parseBoolean(args[3]));
                setProtectTool(false);
                setAllowWrongTool(false);
                setExpPenalty(false);
                break;
            }
            case 5: {
                setChance(args[1]);
                setVeinCap(args[2]);
                setPlaceDropsAtOrigin(Boolean.parseBoolean(args[3]));
                setProtectTool(Boolean.parseBoolean(args[4]));
                setAllowWrongTool(false);
                setExpPenalty(false);
                break;
            }
            case 6: {
                setChance(args[1]);
                setVeinCap(args[2]);
                setPlaceDropsAtOrigin(Boolean.parseBoolean(args[3]));
                setProtectTool(Boolean.parseBoolean(args[4]));
                setAllowWrongTool(Boolean.parseBoolean(args[5]));
                setExpPenalty(false);
                break;
            }
            default: {
                setChance(args[1]);
                setVeinCap(args[2]);
                setPlaceDropsAtOrigin(Boolean.parseBoolean(args[3]));
                setProtectTool(Boolean.parseBoolean(args[4]));
                setAllowWrongTool(Boolean.parseBoolean(args[5]));
                setExpPenalty(Boolean.parseBoolean(args[6]));
            }
        }
    }
}