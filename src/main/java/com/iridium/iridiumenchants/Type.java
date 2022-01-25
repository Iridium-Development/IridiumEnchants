package com.iridium.iridiumenchants;

import com.iridium.iridiumenchants.utils.TypeUtils;
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
            if (TypeUtils.getType(type).map(t -> t.includes(item)).orElse(false)) return true;
        }
        return false;
    }
}
