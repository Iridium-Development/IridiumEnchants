package com.iridium.iridiumenchants.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.gui.EnchantmentListGUI;
import com.iridium.iridiumenchants.gui.EnchantmentTierListGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command which reloads all configuration files.
 */
public class ListCommand extends Command {

    /**
     * The default constructor.
     */
    public ListCommand() {
        super(Collections.singletonList("list"), "List all custom enchantments", "", true, Duration.ZERO);
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
        Player player = (Player) sender;
        if (args.length == 1) {
            player.openInventory(new EnchantmentListGUI(1).getInventory());
        } else {
            Optional<String> tier = IridiumEnchants.getInstance().getConfiguration().tiers.keySet().stream().filter(s -> s.equalsIgnoreCase(args[1])).findAny();
            if (tier.isPresent()) {
                player.openInventory(new EnchantmentTierListGUI(1, tier.get()).getInventory());
            } else {
                sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().noTier.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            }
        }
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
        return IridiumEnchants.getInstance().getConfiguration().tiers.keySet().stream()
                .filter(s -> s.toLowerCase().contains(args[1].toLowerCase()))
                .collect(Collectors.toList());
    }

}
