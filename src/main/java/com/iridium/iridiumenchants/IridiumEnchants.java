package com.iridium.iridiumenchants;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumenchants.commands.Command;
import com.iridium.iridiumenchants.commands.CommandManager;
import com.iridium.iridiumenchants.configs.Commands;
import com.iridium.iridiumenchants.configs.Configuration;
import com.iridium.iridiumenchants.configs.Messages;
import lombok.Getter;

@Getter
public class IridiumEnchants extends IridiumCore {

    private static IridiumEnchants instance;

    private CommandManager commandManager;

    private Configuration configuration;
    private Messages messages;
    private Commands commands;

    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
        this.commandManager = new CommandManager("iridiumenchants");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void registerListeners() {
        super.registerListeners();
    }

    @Override
    public void loadConfigs() {
        this.configuration = getPersist().load(Configuration.class);
        this.messages = getPersist().load(Messages.class);
        this.commands = getPersist().load(Commands.class);
    }

    @Override
    public void saveConfig() {
        getPersist().save(configuration);
        getPersist().save(messages);
        getPersist().save(commands);
    }

    @Override
    public void saveData() {
        super.saveData();
    }

    public static IridiumEnchants getInstance() {
        return instance;
    }
}
