package com.iridium.iridiumenchants.commands.gkits;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.GKit;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command which reloads all configuration files.
 */
public class GiveCommand extends Command {

    /**
     * The default constructor.
     */
    public GiveCommand() {
        super(Collections.singletonList("give"), "Give a player a gkit", "%prefix% &7/gkit give <player> <gkit>", "iridiumenchants.gkits.give", false, Duration.ZERO);
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
        if (args.length != 3) {
            sender.sendMessage(StringUtils.color(syntax.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().notAPlayer.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        Optional<Map.Entry<String, GKit>> gKit = IridiumEnchants.getInstance().getGKits().gkits.entrySet().stream()
                .filter(stringGKitEntry -> stringGKitEntry.getKey().equalsIgnoreCase(args[2]))
                .findFirst();
        if (!gKit.isPresent()) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().noGkit.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().gaveGKit
                .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                .replace("%player%", player.getName())
                .replace("%name%", gKit.get().getKey())
        ));
        player.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().recievedGKit
                .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                .replace("%player%", sender.getName())
                .replace("%name%", gKit.get().getKey())
        ));
        IridiumEnchants.getInstance().getGkitsManager().getItemsFromGkit(gKit.get().getValue()).forEach(itemStack ->
                player.getInventory().addItem(itemStack).values().forEach(item ->
                        player.getWorld().dropItem(player.getLocation(), item)));
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
        if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(HumanEntity::getName)
                    .filter(s -> s.toLowerCase().contains(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args.length == 3) {
            return IridiumEnchants.getInstance().getGKits().gkits.keySet().stream()
                    .filter(s -> s.toLowerCase().contains(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
