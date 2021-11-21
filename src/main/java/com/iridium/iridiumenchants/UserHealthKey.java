package com.iridium.iridiumenchants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class UserHealthKey {
    // Used as the key for this
    private ItemStack key;
    @Setter
    private int extraHealth;
    @Setter
    private int ticksRemaining;

    public void tick() {
        ticksRemaining--;
    }
}
