package com.iridium.iridiumenchants.commands.customenchants;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumenchants.CustomEnchant;
import com.iridium.iridiumenchants.IridiumEnchants;
import com.iridium.iridiumenchants.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

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
        if (args.length != 4) {
            sender.sendMessage(StringUtils.color(syntax.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().notAPlayer.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        Optional<Map.Entry<String, CustomEnchant>> customEnchant = IridiumEnchants.getInstance().getCustomEnchantments().entrySet().stream()
                .filter(stringCustomEnchantEntry ->
                        stringCustomEnchantEntry.getKey().equalsIgnoreCase(args[2])
                ).findFirst();
        if (!customEnchant.isPresent()) {
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
        if (!customEnchant.get().getValue().getLevels().containsKey(level)) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().invalidEnchantmentLevel.replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)));
            return false;
        }
        Collection<ItemStack> itemStacks = player.getInventory().addItem(IridiumEnchants.getInstance().getCustomEnchantManager().getEnchantmentCrystal(customEnchant.get().getKey(), customEnchant.get().getValue(), level)).values();
        for (ItemStack itemStack : itemStacks) {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        }
        if (sender instanceof Player) {
            sender.sendMessage(StringUtils.color(IridiumEnchants.getInstance().getMessages().gavePlayerEnchantment
                    .replace("%prefix%", IridiumEnchants.getInstance().getConfiguration().prefix)
                    .replace("%player%", player.getName())
                    .replace("%enchant%", args[2])
                    .replace("%level%", IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(Integer.parseInt(args[3])))
            ));
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
        if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(HumanEntity::getName)
                    .filter(s -> s.toLowerCase().contains(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args.length == 3) {
            return IridiumEnchants.getInstance().getCustomEnchantments().keySet().stream()
                    .filter(s -> s.toLowerCase().contains(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args.length == 4) {
            Optional<Map.Entry<String, CustomEnchant>> customEnchant = IridiumEnchants.getInstance().getCustomEnchantments().entrySet().stream()
                    .filter(stringCustomEnchantEntry ->
                            stringCustomEnchantEntry.getKey().equalsIgnoreCase(args[2])
                    ).findFirst();
            return customEnchant
                    .map(entry ->
                            entry.getValue().levels.keySet()
                                    .stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.toList())
                    ).orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }

}
