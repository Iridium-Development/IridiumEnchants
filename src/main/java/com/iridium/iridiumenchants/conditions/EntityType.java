package com.iridium.iridiumenchants.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EntityType implements Condition {
    @Override
    public boolean apply(LivingEntity player, LivingEntity target, String[] args, ItemStack item) {
        String operator = args[1];
        String[] entities = args[2].split(",");

        for (String entityType : entities) {
            if (checkEntityType(target, entityType, operator)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkEntityType(LivingEntity target, String entityType, String operator) {
        boolean isMatch = entityType.equalsIgnoreCase("*") || target.getType().name().equalsIgnoreCase(entityType);

        if (operator.equals("==")) {
            return isMatch;
        } else if (operator.equals("!=")) {
            return !isMatch;
        }

        return false;
    }
}