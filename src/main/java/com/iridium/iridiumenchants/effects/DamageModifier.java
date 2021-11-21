package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class DamageModifier implements Effect {
    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
        double modifier;
        try {
            modifier = Double.parseDouble(args[1]);
        } catch (NumberFormatException exception) {
            modifier = 1;
        }
        entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * modifier);
    }
}
