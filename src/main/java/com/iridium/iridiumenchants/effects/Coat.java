package com.iridium.iridiumenchants.effects;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Coat implements Effect {

    public static HashMap<BlockState, Integer> blockStates = new HashMap<>();

    public Coat() {
        Bukkit.getScheduler().runTaskTimer(IridiumEnchants.getInstance(), this::tick, 0, 1);
    }

    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, Event event) {
        if (!(player instanceof Player)) return;
        int radius;
        try {
            radius = Integer.parseInt(args[2]);
        } catch (NumberFormatException exception) {
            radius = 1;
        }
        Optional<XMaterial> material = XMaterial.matchXMaterial(args[1].toUpperCase());
        if (!material.isPresent()) return;
        if (args.length == 4 && args[3].equalsIgnoreCase("target")) {
            if (target == null) return;
            coat((Player) player, target, radius, material.get().parseMaterial());

        } else {
            coat((Player) player, player, radius, material.get().parseMaterial());
        }
    }

    public void coat(Player player, LivingEntity livingEntity, int radius, Material material) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location location = livingEntity.getLocation().add(x, y, z).getBlock().getLocation();
                    Block block = location.getBlock();
                    if (IridiumEnchants.getInstance().canBuild((player), block.getLocation())) {
                        Block above = location.clone().add(0, 1, 0).getBlock();
                        blockStates.keySet().stream().filter(blockState -> blockState.getLocation().equals(location)).findAny().ifPresent(blockState -> blockStates.put(blockState, 20));
                        if (block.getType().isSolid() && above.getType() == Material.AIR) {
                            BlockState blockState = above.getState();
                            blockStates.put(blockState, 20);
                            above.setType(material, false);
                        }
                    }
                }
            }
        }
    }

    public void tick() {
        for (BlockState blockState : new ArrayList<>(blockStates.keySet())) {
            blockStates.put(blockState, blockStates.get(blockState) - 1);
            if (blockStates.get(blockState) == 0) {
                blockState.update(true, false);
                blockStates.remove(blockState);
            }
        }
    }
}
