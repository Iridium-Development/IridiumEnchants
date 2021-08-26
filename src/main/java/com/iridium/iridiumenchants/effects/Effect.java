package com.iridium.iridiumenchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public interface Effect {
    void apply(LivingEntity player, LivingEntity target,  String[] args, Event event);
}
