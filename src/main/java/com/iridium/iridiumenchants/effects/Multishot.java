package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumenchants.listeners.EntityShootBowListener;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Multishot implements Effect {

    private final List<EntityShootBowEvent> events = new ArrayList<>();

    @Override
    public void apply(LivingEntity p, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (!(event instanceof EntityShootBowEvent) || !(p instanceof Player)) return;
        EntityShootBowEvent entityShootBowEvent = (EntityShootBowEvent) event;
        if (events.contains(entityShootBowEvent)) {
            events.remove(entityShootBowEvent);
            return;
        }
        Player player = (Player) p;
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            amount = 1;
        }
        Vector velocity = entityShootBowEvent.getProjectile().getVelocity().clone();

        final double speed = velocity.length();
        final Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
        for (int i = 0; i < amount; i++) {
            ItemStack item = new ItemStack(Material.ARROW);
            if (player.getInventory().containsAtLeast(item, 1) || !entityShootBowEvent.shouldConsumeItem()) {
                if (entityShootBowEvent.shouldConsumeItem()) player.getInventory().removeItem(item);
                Arrow arrow = p.launchProjectile(Arrow.class);
                arrow.setFireTicks(entityShootBowEvent.getProjectile().getFireTicks());
                arrow.setBounce(false);
                arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                        direction.getY() + (Math.random() - 0.5D) / 3.5D,
                        direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize()
                        .multiply(speed));
                arrow.setShooter(p);
                arrow.setPickupStatus(entityShootBowEvent.shouldConsumeItem() ? AbstractArrow.PickupStatus.ALLOWED : AbstractArrow.PickupStatus.CREATIVE_ONLY);
                EntityShootBowEvent newEntityShootBowEvent = new EntityShootBowEvent(p, entityShootBowEvent.getBow(), item, arrow, entityShootBowEvent.getHand(), entityShootBowEvent.getForce(), entityShootBowEvent.shouldConsumeItem());
                events.add(newEntityShootBowEvent);
                new EntityShootBowListener().onEntityShootBow(newEntityShootBowEvent);
            }
        }
    }
}
