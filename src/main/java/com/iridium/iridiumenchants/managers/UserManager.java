package com.iridium.iridiumenchants.managers;

import com.iridium.iridiumenchants.User;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<UUID, User> users = new HashMap<>();

    public User getUser(LivingEntity offlinePlayer) {
        if (users.containsKey(offlinePlayer.getUniqueId())) {
            return users.get(offlinePlayer.getUniqueId());
        } else {
            User user = new User(offlinePlayer.getUniqueId());
            users.put(offlinePlayer.getUniqueId(), user);
            return user;
        }
    }
}
