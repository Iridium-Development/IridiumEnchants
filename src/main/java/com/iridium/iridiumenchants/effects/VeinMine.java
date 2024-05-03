package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchants;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class VeinMine implements Effect {

    private final HashSet<Block> blocksBuffer = new HashSet<>(), recent = new HashSet<>();
    private boolean safetyFired = false;

    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (player instanceof Player && event instanceof BlockBreakEvent) {
            Player realPlayer = (Player) player;
            BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
            Block eventBlock = blockBreakEvent.getBlock();

            if (IridiumEnchants.getInstance().getConfiguration().veinMiner.contains(XMaterial.matchXMaterial(eventBlock.getType()))) {
                blockBreakEvent.setCancelled(true);

                ItemStack mainHand = realPlayer.getInventory().getItemInMainHand();
                ItemStack offHand = realPlayer.getInventory().getItemInOffHand();
                ItemStack usedTool;
                if(isTool(mainHand)) usedTool = mainHand;
                else usedTool = offHand;

                HashSet<Block> blocksToMine = allocateBlocks(eventBlock);
                int blockCount = blocksToMine.size();

                int chance;
                if (args.length < 2) chance = 100;
                else {
                    try {
                        chance = Integer.parseInt(args[1]);
                    } catch (NumberFormatException exception) {
                        chance = 100;
                    }
                }

                if(eventBlock.getDrops(usedTool).isEmpty()) {
                    String displayName;
                    if(usedTool.getItemMeta().hasDisplayName()) displayName = usedTool.getItemMeta().getDisplayName();
                    else displayName = usedTool.getType().toString().toLowerCase();

                    player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().veinMineWrongTool
                            .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                            .replace("%tool%", WordUtils.capitalize(displayName.replace("_", " ")))
                    ));
                    return;
                }

                if(usedTool.getItemMeta() instanceof Damageable) {

                    int toolDamage = damageTool(blockCount, usedTool, chance);

                    if (safetyFired) {

                        String displayName;
                        if(usedTool.getItemMeta().hasDisplayName()) displayName = usedTool.getItemMeta().getDisplayName();
                        else displayName = usedTool.getType().toString().toLowerCase();

                        player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().cannotVeinMine
                                .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                                .replace("%tool%", WordUtils.capitalize(displayName.replace("_", " ")))
                        ));
                        safetyFired = false;
                        return;
                    }

                    Damageable toolMeta = (Damageable) usedTool.getItemMeta();
                    toolMeta.setDamage(toolDamage);
                    usedTool.setItemMeta(toolMeta);
                }

                if (IridiumEnchants.getInstance().getConfiguration().veinMinerDropsAtBreakLocation) {
                    ArrayList<Collection<ItemStack>> blockDrops = new ArrayList<>();

                    for (Block adjacentBlock : blocksToMine) {
                        blockDrops.add(adjacentBlock.getDrops(usedTool));
                    }

                    for (Collection<ItemStack> drops : blockDrops) {
                        for (ItemStack item : drops) {
                            eventBlock.getLocation().getWorld().dropItemNaturally(eventBlock.getLocation(), item);
                        }
                    }

                } else {
                    for (Block adjacentBlock : blocksToMine) {
                        for (ItemStack item : adjacentBlock.getDrops(usedTool)) {
                            adjacentBlock.getLocation().getWorld().dropItemNaturally(adjacentBlock.getLocation(), item);
                        }
                    }
                }

                for(Block block : blocksToMine) {
                    block.setType(getAir(block));
                }
            }
        }
    }

    private boolean isTool(ItemStack itemStack) {
        return itemStack.getType().name().toUpperCase().contains("_AXE")
                || itemStack.getType().name().toUpperCase().contains("PICKAXE")
                || itemStack.getType().name().toUpperCase().contains("SHOVEL")
                || itemStack.getType().name().toUpperCase().contains("HOE");
    }

    private int damageTool(int blockCount, ItemStack itemStack, int chance) {

        ItemMeta toolMeta = itemStack.getItemMeta();
        int toolDamage = 0;
        if (toolMeta instanceof Damageable) {
            Damageable realToolMeta = (Damageable) toolMeta;
            int maxToolDamage = itemStack.getType().getMaxDurability();
            toolDamage = realToolMeta.getDamage();

            Random random = new Random();
            int rolledChance = random.nextInt(101);
            if (chance <= rolledChance) {
                if (toolDamage + blockCount >= maxToolDamage && !IridiumEnchants.getInstance().getConfiguration().veinMinerBreaksTools) {
                    safetyFired = true;
                    toolDamage = maxToolDamage - 1;
                } else { toolDamage = toolDamage + blockCount; }
            }
        }

        return toolDamage;
    }

    public HashSet<Block> allocateBlocks(Block block) {
        HashSet<Block> blocks = new HashSet<>();
        this.recent.add(block);
        int maxVeinSize = IridiumEnchants.getInstance().getConfiguration().veinMinerCap;

        while (blocksBuffer.size() < maxVeinSize) {
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

                            if (blocks.size() + blocksBuffer.size() >= maxVeinSize) {
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

    public Material getAir(Block block) {
        for(BlockFace blockFace : BlockFace.values()) {
            if(block.getRelative(blockFace).getType() == Material.CAVE_AIR) return Material.CAVE_AIR;
        }
        return Material.AIR;
    }
}
