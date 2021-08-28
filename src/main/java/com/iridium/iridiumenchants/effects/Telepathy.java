package com.iridium.iridiumenchants.effects;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class Telepathy implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        if (!(event instanceof BlockBreakEvent) || !(player instanceof Player)) return;
        Player p = (Player) player;
        BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
        blockBreakEvent.setCancelled(true);
        for (ItemStack itemStack : blockBreakEvent.getBlock().getDrops(p.getItemInHand(), p)) {
            Collection<ItemStack> items = p.getInventory().addItem(itemStack).values();
            for (ItemStack item : items) {
                blockBreakEvent.getBlock().getWorld().dropItem(blockBreakEvent.getBlock().getLocation(), item);
            }
        }
        blockBreakEvent.getBlock().setType(Material.AIR);
    }
}
