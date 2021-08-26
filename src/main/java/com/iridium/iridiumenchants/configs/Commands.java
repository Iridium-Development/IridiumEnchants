package com.iridium.iridiumenchants.configs;

import com.iridium.iridiumenchants.commands.GiveCommand;
import com.iridium.iridiumenchants.commands.HelpCommand;
import com.iridium.iridiumenchants.commands.ReloadCommand;

public class Commands {
    public HelpCommand helpCommand = new HelpCommand();
    public ReloadCommand reloadCommand = new ReloadCommand();
    public GiveCommand giveCommand = new GiveCommand();
}
