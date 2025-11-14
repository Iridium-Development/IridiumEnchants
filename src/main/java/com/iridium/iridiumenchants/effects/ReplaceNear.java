package com.iridium.iridiumenchants.effects;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ReplaceNear implements Effect {

    public static HashMap<BlockState, Integer> blockStates = new HashMap<>();

    public ReplaceNear() {
        Bukkit.getScheduler().runTaskTimer(IridiumEnchants.getInstance(), this::tick, 0, 1);
    }

    @Override
    public void apply(LivingEntity player, LivingEntity target, String[] args, ItemStack itemStack, Event event) {
        if (!(player instanceof Player)) return;
        int radius;
        try {
            radius = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            radius = 1;
        }
        int time;
        try {
            time = Integer.parseInt(args[4]);
        } catch (NumberFormatException exception) {
            time = 20;
        }
        Optional<XMaterial> originalMaterial = XMaterial.matchXMaterial(args[2].toUpperCase());
        Optional<XMaterial> newMaterial = XMaterial.matchXMaterial(args[3].toUpperCase());
        if (!originalMaterial.isPresent() || !newMaterial.isPresent()) return;
        if (args.length == 6 && args[5].equalsIgnoreCase("target")) {
            if (target == null) return;
            replaceNear((Player) player, target, radius, originalMaterial.get(), newMaterial.get(), time);

        } else {
            replaceNear((Player) player, player, radius, originalMaterial.get(), newMaterial.get(), time);
        }
    }

    public void replaceNear(Player player, LivingEntity livingEntity, int radius, XMaterial currentMaterial, XMaterial newMaterial, int time) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location location = livingEntity.getLocation().add(x, y, z).getBlock().getLocation();
                    Block block = location.getBlock();
                    if (IridiumEnchants.getInstance().canBuild((player), block.getLocation())) {
                        blockStates.keySet().stream().filter(blockState -> blockState.getLocation().equals(location)).findAny().ifPresent(blockState -> blockStates.put(blockState, time));
                        if (XMaterial.matchXMaterial(block.getType()) == currentMaterial) {
                            Material m = newMaterial.get();
                            if(m != null){
                                BlockState blockState = block.getState();
                                blockStates.put(blockState, time);
                                block.setType(newMaterial.get(), false);
                            }
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
                blockState.update(true, true);
                blockStates.remove(blockState);
            }
        }
    }
}
