package com.iridium.iridiumenchants;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Material;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Type {
    public List<String> types;

    public boolean includes(Material item) {
        for (String type : types) {
            if (item.name().equalsIgnoreCase(type)) return true;
            if (IridiumEnchants.getInstance().getTypes().types.containsKey(type)) {
                if (IridiumEnchants.getInstance().getTypes().types.get(type).includes(item)) return true;
            }
        }
        return false;
    }
}
