package com.iridium.iridiumenchants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Level {
    public double chance;
    public List<String> tiers;
    public List<String> effects;
    public List<String> conditions;
}
