package com.iridium.iridiumenchants;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumenchants.commands.CommandManager;
import com.iridium.iridiumenchants.configs.Commands;
import com.iridium.iridiumenchants.configs.Configuration;
import com.iridium.iridiumenchants.configs.CustomEnchants;
import com.iridium.iridiumenchants.configs.Messages;
import com.iridium.iridiumenchants.listeners.InventoryClickListener;
import com.iridium.iridiumenchants.listeners.PlayerInteractListener;
import com.iridium.iridiumenchants.managers.CustomEnchantManager;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class IridiumEnchants extends IridiumCore {

    private static IridiumEnchants instance;

    private CommandManager commandManager;
    private CustomEnchantManager customEnchantManager;

    private Configuration configuration;
    private Messages messages;
    private Commands commands;
    private CustomEnchants customEnchants;

    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
        this.commandManager = new CommandManager("iridiumenchants");
        this.customEnchantManager = new CustomEnchantManager();

        getLogger().info("----------------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Enabled!");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info("");
        getLogger().info("----------------------------------------");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
    }

    @Override
    public void loadConfigs() {
        this.configuration = getPersist().load(Configuration.class);
        this.messages = getPersist().load(Messages.class);
        this.commands = getPersist().load(Commands.class);
        this.customEnchants = getPersist().load(CustomEnchants.class);
    }

    @Override
    public void saveConfigs() {
        getPersist().save(configuration);
        getPersist().save(messages);
        getPersist().save(commands);
        getPersist().save(customEnchants);
    }

    @Override
    public void saveData() {
        super.saveData();
    }

    public static IridiumEnchants getInstance() {
        return instance;
    }
}
