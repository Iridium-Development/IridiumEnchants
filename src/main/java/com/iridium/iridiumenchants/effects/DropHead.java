package com.iridium.iridiumenchants.effects;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DropHead implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (args.length == 2 && args[1].equalsIgnoreCase("target")) {
            if (target == null) return;
            ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
            if (head == null) return;
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwner(target.getName());
            head.setItemMeta(meta);
            target.getLocation().getWorld().dropItem(target.getLocation(), head);
        } else {
            if (player == null) return;
            ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
            if (head == null) return;
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwner(player.getName());
            head.setItemMeta(meta);
            player.getLocation().getWorld().dropItem(player.getLocation(), head);
        }
    }
}
