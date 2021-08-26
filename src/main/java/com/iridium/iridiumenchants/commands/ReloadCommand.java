package com.iridium.iridiumenchants.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * Command which reloads all configuration files.
 */
public class ReloadCommand extends Command {

    /**
     * The default constructor.
     */
    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload the plugin configurations", "iridiumenchants.reload", false, Duration.ZERO);
    }

    /**
     * Executes the command for the specified {@link CommandSender} with the provided arguments.
     * Not called when the command execution was invalid (no permission, no player or command disabled).
     * Reloads all configuration files.
     *
     * @param sender The CommandSender which executes this command
     * @param args   The arguments used with this command. They contain the sub-command
     */
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        IridiumEnchants.getInstance().loadConfigs();
        sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().reloaded.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
        return true;
    }

    /**
     * Handles tab-completion for this command.
     *
     * @param commandSender The CommandSender which tries to tab-complete
     * @param command       The command
     * @param label         The label of the command
     * @param args          The arguments already provided by the sender
     * @return The list of tab completions for this command
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        // We currently don't want to tab-completion here
        // Return a new List so it isn't a list of online players
        return Collections.emptyList();
    }

}
