package com.iridium.iridiumenchants.listeners;

import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            if (event.getEntity() instanceof Player) {
                List<ItemStack> itemStackList = Arrays.asList(
                        killer.getItemInHand(),
                        killer.getInventory().getBoots(),
                        killer.getInventory().getLeggings(),
                        killer.getInventory().getChestplate(),
                        killer.getInventory().getHelmet()
                );
                for (ItemStack itemStack : itemStackList) {
                    IridiumEnchants.getInstance().getCustomEnchantManager().applyEffectsFromItem(itemStack, trigger -> trigger.equalsIgnoreCase("PLAYER_KILL"), killer, event.getEntity(), event);
                }
            }
            List<ItemStack> itemStackList = Arrays.asList(
                    killer.getItemInHand(),
                    killer.getInventory().getBoots(),
                    killer.getInventory().getLeggings(),
                    killer.getInventory().getChestplate(),
                    killer.getInventory().getHelmet()
            );
            for (ItemStack itemStack : itemStackList) {
                IridiumEnchants.getInstance().getCustomEnchantManager().applyEffectsFromItem(itemStack, trigger -> trigger.equalsIgnoreCase("ENTITY_KILL"), killer, event.getEntity(), event);
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
                IridiumEnchants.getInstance().getCustomEnchantManager().applyEffectsFromItem(itemStack, trigger -> trigger.equalsIgnoreCase("PLAYER_DEATH"), player, killer, event);
            }
        }
    }

}
