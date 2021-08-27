package com.iridium.iridiumenchants.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.Collections;
import java.util.List;


public class AboutCommand extends Command {
    public AboutCommand() {
        super(Collections.singletonList("about"), "Display plugin info", "", false, Duration.ZERO);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(StringUtils.color("&7Plugin Name: &eIridiumEnchants"));
        sender.sendMessage(StringUtils.color("&7Plugin Version: &e" + IridiumEnchants.getInstance().getDescription().getVersion()));
        sender.sendMessage(StringUtils.color("&7Plugin Author: &ePeaches_MLG"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        // We currently don't want to tab-completion here
        // Return a new List so it isn't a list of online players
        return Collections.emptyList();
    }

}
