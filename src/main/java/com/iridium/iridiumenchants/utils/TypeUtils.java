package com.iridium.iridiumenchants.utils;

import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.Type;

import java.util.Map;
import java.util.Optional;

public class TypeUtils {

    public static Optional<Type> getType(String type){
        for(Map.Entry<String, Type> types : IridiumEnchants.getInstance().getTypes().types.entrySet()){
            if(types.getKey().equalsIgnoreCase(type)){
                return Optional.of(types.getValue());
            }
        }
        return Optional.empty();
    }
}
