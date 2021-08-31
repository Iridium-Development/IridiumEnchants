package com.iridium.iridiumenchants.commands.customenchants;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CooldownProvider;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.commands.Command;
import com.iridium.iridiumenchants.configs.Commands;
import com.iridium.iridiumenchants.gui.EnchantmentTierGUI;
import com.iridium.iridiumenchants.utils.TimeUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Handles command executions and tab-completions for all commands of this plugin.
 */
public class CommandManager implements CommandExecutor, TabCompleter {

    public final List<Command> commands = new ArrayList<>();

    /**
     * The default constructor.
     *
     * @param command The base command of the plugin
     */
    public CommandManager(String command) {
        IridiumEnchants.getInstance().getCommand(command).setExecutor(this);
        IridiumEnchants.getInstance().getCommand(command).setTabCompleter(this);
        registerCommands();
    }

    /**
     * Registers all commands of this plugin.
     */
    public void registerCommands() {
        Commands commands = IridiumEnchants.getInstance().getCommands();
        registerCommand(commands.helpCommand);
        registerCommand(commands.reloadCommand);
        registerCommand(commands.giveCommand);
        registerCommand(commands.listCommand);
        registerCommand(commands.aboutCommand);
    }

    /**
     * Registers a single command in the command system.
     *
     * @param command The command which should be registered
     */
    public void registerCommand(Command command) {
        if (command.enabled) {
            int index = Collections.binarySearch(commands, command, Comparator.comparing(cmd -> cmd.aliases.get(0)));
            commands.add(index < 0 ? -(index + 1) : index, command);
        }
    }

    /**
     * Unregisters a single command in the command system.
     *
     * @param command The command which should be unregistered
     */
    public void unregisterCommand(Command command) {
        commands.remove(command);
    }

    /**
     * Reloads all commands
     */
    public void reloadCommands() {
        commands.clear();
        registerCommands();
    }

    /**
     * Method which handles command execution for all sub-commands.
     * Automatically checks if a User can execute the command.
     * All parameters are provided by Bukkit.
     *
     * @param commandSender The sender which executes this command
     * @param cmd           The Bukkit {@link org.bukkit.command.Command} representation
     * @param label         The label of this command. Not used.
     * @param args          The arguments of this command
     * @return true if this command was executed successfully
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof Player && IridiumEnchants.getInstance().getConfiguration().tierGUI) {
                ((Player) commandSender).openInventory(new EnchantmentTierGUI().getInventory());
            } else {
                IridiumEnchants.getInstance().getCommands().helpCommand.execute(commandSender, args);
            }
            return true;
        }

        for (Command command : commands) {
            // We don't want to execute other commands or ones that are disabled
            if (!(command.aliases.contains(args[0]))) {
                continue;
            }

            // Check if this command is only for players
            if (command.onlyForPlayers && !(commandSender instanceof Player)) {
                // Must be a player
                commandSender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().mustBeAPlayer
                        .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
                return false;
            }

            // Check permissions
            if (!((commandSender.hasPermission(command.permission) || command.permission
                    .equalsIgnoreCase("") || command.permission
                    .equalsIgnoreCase("IridiumEnchants.")))) {
                // No permissions
                commandSender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().noPermission
                        .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
                return false;
            }

            // Check cooldown
            CooldownProvider<CommandSender> cooldownProvider = command.getCooldownProvider();
            if (commandSender instanceof Player && cooldownProvider.isOnCooldown(commandSender)) {
                Duration remainingTime = cooldownProvider.getRemainingTime(commandSender);
                String formattedTime = TimeUtils.formatDuration(IridiumEnchants.getInstance().getMessages().activeCooldown, remainingTime);

                commandSender.sendMessage(StringUtils.color(formattedTime.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
                return false;
            }

            boolean success = command.execute(commandSender, args);
            if (success) cooldownProvider.applyCooldown(commandSender);
            return true;
        }

        // Unknown command message
        commandSender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().unknownCommand.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
        return false;
    }

    /**
     * Method which handles tab-completion of the main command and all sub-commands.
     *
     * @param commandSender The CommandSender which tries to tab-complete
     * @param cmd           The command
     * @param label         The label of the command
     * @param args          The arguments already provided by the sender
     * @return The list of tab completions for this command
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String[] args) {
        // Handle the tab completion if it's the sub-command selection
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (Command command : commands) {
                for (String alias : command.aliases) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (
                            (commandSender.hasPermission(command.permission)
                                    || command.permission.equalsIgnoreCase("") || command.permission
                                    .equalsIgnoreCase("IridiumEnchants.")))) {
                        result.add(alias);
                    }
                }
            }
            return result;
        }

        // Let the sub-command handle the tab completion
        for (Command command : commands) {
            if (command.aliases.contains(args[0]) && (
                    commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")
                            || command.permission.equalsIgnoreCase("IridiumEnchants."))) {
                return command.onTabComplete(commandSender, cmd, label, args);
            }
        }

        // We currently don't want to tab-completion here
        // Return a new List so it isn't a list of online players
        return Collections.emptyList();
    }

}
