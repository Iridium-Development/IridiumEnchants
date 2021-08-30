package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.effects.Coat;
import com.iridium.iridiumenchants.effects.ReplaceNear;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class BlockBreakListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        ReplaceNear.blockStates.keySet().stream()
                .filter(blockState -> blockState.getBlock().equals(event.getBlock()))
                .findAny().ifPresent(blockState -> {
            event.setCancelled(true);
            blockState.update(true, false);
            ReplaceNear.blockStates.remove(blockState);
        });
        Coat.blockStates.keySet().stream()
                .filter(blockState -> blockState.getBlock().equals(event.getBlock()))
                .findAny().ifPresent(blockState -> {
            event.setCancelled(true);
            blockState.update(true, false);
            Coat.blockStates.remove(blockState);
        });
        Player player = event.getPlayer();

        List<ItemStack> itemStackList = Arrays.asList(
                player.getItemInHand(),
                player.getInventory().getBoots(),
                player.getInventory().getLeggings(),
                player.getInventory().getChestplate(),
                player.getInventory().getHelmet()
        );
        for (ItemStack itemStack : itemStackList) {
            IridiumEnchants.getInstance().getCustomEnchantManager().applyEffectsFromItem(itemStack, trigger -> trigger.equalsIgnoreCase("BLOCK_BREAK"), player, null, event);
        }
    }

}
