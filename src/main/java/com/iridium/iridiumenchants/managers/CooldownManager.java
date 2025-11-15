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

    public CompletableFuture<Void> applyCooldown(UUID uuid, String cooldownKey, LocalDateTime expiresAt) {
        return CompletableFuture.runAsync(() -> saveCooldown(uuid, cooldownKey, expiresAt), executor);
    }

    public CompletableFuture<LocalDateTime> getCooldown(UUID uuid, String cooldownKey) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "SELECT expires_at FROM cooldowns WHERE player_uuid = ? AND cooldown_key = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, uuid.toString());
                ps.setString(2, cooldownKey);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return LocalDateTime.parse(rs.getString("expires_at"));
                    } else {
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