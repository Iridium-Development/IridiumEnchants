package com.iridium.iridiumenchants.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.IridiumEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Command which reloads all configuration files.
 */
public class GiveCommand extends Command {

    /**
     * The default constructor.
     */
    public GiveCommand() {
        super(Collections.singletonList("give"), "Give a player an enchantment crystal", "%prefix% &7/ce give <player> <enchant> <level>", "iridiumenchants.give", false, Duration.ZERO);
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
        // /ce give <player> <Enchant> <Level>
        if (args.length != 4) {
            sender.sendMessage(StringUtils.color(syntax.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().notAPlayer.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        Optional<IridiumEnchant> iridiumEnchant = IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantment(args[2]);
        if (!iridiumEnchant.isPresent()) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().notAnEnchantment.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        int level;
        try {
            level = Integer.parseInt(args[3]);
        } catch (NumberFormatException exception) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().notANumber.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        if (!iridiumEnchant.get().getCustomEnchant().getLevels().containsKey(level)) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().invalidEnchantmentLevel.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        player.getInventory().addItem(IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentCrystal(iridiumEnchant.get(), level));
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
