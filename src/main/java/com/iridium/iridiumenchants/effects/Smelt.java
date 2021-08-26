package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Smelt implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        if (player instanceof Player && event instanceof BlockBreakEvent) {
            BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
            blockBreakEvent.setDropItems(false);
            Block block = blockBreakEvent.getBlock();
            for (ItemStack drop : block.getDrops(((Player) player).getItemInHand())) {
                XMaterial xMaterial = XMaterial.matchXMaterial(drop);
                ItemStack itemStack = IridiumEnchants.getInstance().getConfiguration().smelt.getOrDefault(xMaterial, xMaterial).parseItem();
                if (itemStack != null) {
                    block.getLocation().getWorld().dropItem(block.getLocation(), itemStack);
                }
            }
        }
    }
}
