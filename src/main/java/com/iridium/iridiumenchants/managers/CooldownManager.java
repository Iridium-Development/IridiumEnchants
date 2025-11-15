package com.iridium.iridiumenchants.managers;

import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.configs.SQL;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.*;
import java.sql.*;

public class CooldownManager {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final Connection connection;

    private final ConcurrentMap<String, LocalDateTime> cache = new ConcurrentHashMap<>();

    public CooldownManager(SQL sqlConfig) throws SQLException {
        this.connection = DriverManager.getConnection(getDatabaseURL(sqlConfig));
        initTable();
    }

    private void initTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS cooldowns (player_uuid TEXT, cooldown_key TEXT, expires_at TEXT, PRIMARY KEY(player_uuid, cooldown_key))";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    private String buildKey(UUID uuid, String cooldownKey) {
        return uuid.toString() + ":" + cooldownKey;
    }

    public CompletableFuture<Void> applyCooldown(UUID uuid, String cooldownKey, LocalDateTime expiresAt) {
        return CompletableFuture.runAsync(() -> {
            saveCooldown(uuid, cooldownKey, expiresAt);
            cache.put(buildKey(uuid, cooldownKey), expiresAt);
        }, executor);
    }

    public CompletableFuture<LocalDateTime> getCooldown(UUID uuid, String cooldownKey) {
        return CompletableFuture.supplyAsync(() -> {
            String cacheKey = buildKey(uuid, cooldownKey);

            LocalDateTime cachedExpiry = cache.get(cacheKey);
            if(cachedExpiry != null){
                return cachedExpiry;
            }

            IridiumEnchants.getInstance().getLogger().info("Getting cooldown from SQL");
            String sql = "SELECT expires_at FROM cooldowns WHERE player_uuid = ? AND cooldown_key = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, uuid.toString());
                ps.setString(2, cooldownKey);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        LocalDateTime expiresAt = LocalDateTime.parse(rs.getString("expires_at"));
                        cache.put(cacheKey, expiresAt);
                        return expiresAt;
                    } else {
                        cache.put(cacheKey, LocalDateTime.MIN);
                        return LocalDateTime.MIN;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return LocalDateTime.MAX;
        }, executor);
    }

    private void saveCooldown(UUID uuid, String cooldownKey, LocalDateTime expiresAt) {
        String sql = "REPLACE INTO cooldowns (player_uuid, cooldown_key, expires_at) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uuid.toString());
            ps.setString(2, cooldownKey);
            ps.setString(3, expiresAt.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private @NotNull String getDatabaseURL(SQL sqlConfig) {
        switch (sqlConfig.driver) {
            case MYSQL:
                return "jdbc:" + sqlConfig.driver.name().toLowerCase() + "://" + sqlConfig.host + ":" + sqlConfig.port + "/" + sqlConfig.database + "?useSSL=" + sqlConfig.useSSL;
            case SQLITE:
                return "jdbc:sqlite:" + new File(IridiumEnchants.getInstance().getDataFolder(), sqlConfig.database + ".db");
            default:
                throw new UnsupportedOperationException("Unsupported driver (how did we get here?): " + sqlConfig.driver.name());
        }
    }
}