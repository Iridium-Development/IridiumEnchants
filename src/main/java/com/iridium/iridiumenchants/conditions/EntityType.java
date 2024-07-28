package com.iridium.iridiumenchants.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EntityType implements Condition {
    @Override
    public boolean apply(LivingEntity player, LivingEntity target, String[] args, ItemStack item) {
        String operator = args[1];
        String[] entities = args[2].trim().split(",");

        if (operator.equals("==")) {
            return Arrays.stream(entities).anyMatch(entityType -> checkEntityType(target, entityType, operator));
        } else if (operator.equals("!=")) {
            return Arrays.stream(entities).allMatch(entityType -> checkEntityType(target, entityType, operator));
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