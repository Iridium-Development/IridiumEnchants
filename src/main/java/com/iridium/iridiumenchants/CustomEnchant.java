package com.iridium.iridiumenchants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomEnchant {
    public String displayName;
    public String description;
    public Type type;
    public Trigger trigger;
    public Map<Integer, Level> levels;
    public Boolean enabled;
}
