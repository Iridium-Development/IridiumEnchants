package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EntityDamageListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!(event.getDamager() instanceof LivingEntity)) return;
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            List<ItemStack> itemStackList = Arrays.asList(
                    player.getItemInHand(),
                    player.getInventory().getBoots(),
                    player.getInventory().getLeggings(),
                    player.getInventory().getChestplate(),
                    player.getInventory().getHelmet()
            );
            for (ItemStack itemStack : itemStackList) {
                IridiumEnchants.getInstance().getCustomEnchantManager().applyEffectsFromItem(itemStack, trigger -> trigger.equalsIgnoreCase("PLAYER_DAMAGE"), player, (LivingEntity) event.getEntity());
            }
        }
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            List<ItemStack> itemStackList = Arrays.asList(
                    player.getItemInHand(),
                    player.getInventory().getBoots(),
                    player.getInventory().getLeggings(),
                    player.getInventory().getChestplate(),
                    player.getInventory().getHelmet()
            );
            for (ItemStack itemStack : itemStackList) {
                IridiumEnchants.getInstance().getCustomEnchantManager().applyEffectsFromItem(itemStack, trigger -> trigger.equalsIgnoreCase("DEFENCE"), player, (LivingEntity) event.getDamager());
            }
        }
    }

}
