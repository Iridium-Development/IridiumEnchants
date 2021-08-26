package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class Infusion implements Effect {

    private List<BlockBreakEvent> events = new ArrayList<>();

    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        if (player instanceof Player && event instanceof BlockBreakEvent) {
            BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
            if (events.contains(blockBreakEvent)) {
                events.remove(blockBreakEvent);
                return;
            }
            int radius;
            try {
                radius = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                radius = 1;
            }
            boolean instantMine = args[2].equalsIgnoreCase("true");
            for (Block block : getSquare(blockBreakEvent.getBlock().getLocation(), radius)) {
                XMaterial material = XMaterial.matchXMaterial(block.getType());
                if (IridiumEnchants.getInstance().getConfiguration().infusionBlacklist.contains(material)) continue;
                BlockBreakEvent breakEvent = new BlockBreakEvent(block, (Player) player);
                events.add(breakEvent);
                Bukkit.getPluginManager().callEvent(breakEvent);
                if (blockBreakEvent.isCancelled()) continue;
                if (instantMine) {
                    block.setType(Material.AIR);
                } else {
                    block.breakNaturally(((Player) player).getItemInHand());
                }
            }
        }
    }

    public List<Block> getSquare(Location loc, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    blocks.add(b);
                }
            }
        }
        return blocks;
    }
}
