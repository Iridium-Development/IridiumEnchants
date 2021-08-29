package com.iridium.iridiumenchants;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumenchants.commands.CommandManager;
import com.iridium.iridiumenchants.conditions.*;
import com.iridium.iridiumenchants.configs.*;
import com.iridium.iridiumenchants.effects.*;
import com.iridium.iridiumenchants.listeners.*;
import com.iridium.iridiumenchants.managers.CustomEnchantManager;
import com.iridium.iridiumenchants.managers.UserManager;
import com.iridium.iridiumenchants.support.AntiCheatSupport;
import com.iridium.iridiumenchants.support.BuildSupport;
import com.iridium.iridiumenchants.support.FriendlySupport;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class IridiumEnchants extends IridiumCore {

    private static IridiumEnchants instance;

    private CommandManager commandManager;
    private CustomEnchantManager customEnchantManager;
    private UserManager userManager;

    private Configuration configuration;
    private Messages messages;
    private Commands commands;
    private CustomEnchants customEnchants;
    private Inventories inventories;

    private Map<String, Effect> effects;
    private Map<String, Condition> conditions;

    private BuildSupport buildSupport;
    private FriendlySupport friendlySupport;
    private AntiCheatSupport antiCheatSupport;

    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
        this.commandManager = new CommandManager("iridiumenchants");
        this.customEnchantManager = new CustomEnchantManager();
        this.userManager = new UserManager();

        for (Player player : Bukkit.getOnlinePlayers()) {
            userManager.getUser(player);
        }

        registerEffects();
        registerConditions();
        registerSupport();

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
        for (BlockState blockState : ReplaceNear.blockStates.keySet()) {
            blockState.update(true, false);
        }
    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityShootBowListener(), this);
        Bukkit.getPluginManager().registerEvents(new CraftItemListener(), this);
    }

    @Override
    public void loadConfigs() {
        this.configuration = getPersist().load(Configuration.class);
        this.messages = getPersist().load(Messages.class);
        this.commands = getPersist().load(Commands.class);
        this.customEnchants = getPersist().load(CustomEnchants.class);
        this.inventories = getPersist().load(Inventories.class);
    }

    @Override
    public void saveConfigs() {
        getPersist().save(configuration);
        getPersist().save(messages);
        getPersist().save(commands);
        getPersist().save(customEnchants);
        getPersist().save(inventories);
    }

    @Override
    public void saveData() {
        super.saveData();
    }

    public void registerSupport() {
        this.buildSupport = (player, location) -> true;
        this.friendlySupport = (player, livingEntity) -> false;
        this.antiCheatSupport = new AntiCheatSupport() {
            @Override
            public void exemptPlayer(Player player) {
            }

            @Override
            public void unExemptPlayer(Player player) {
            }
        };
    }

    public void registerEffects() {
        effects = new HashMap<>();
        effects.put("POTION", new Potion());
        effects.put("FEED", new Feed());
        effects.put("FIRE", new Fire());
        effects.put("EXPLODE", new Explode());
        effects.put("SMELT", new Smelt());
        effects.put("INFUSION", new Infusion());
        effects.put("AURA", new Aura());
        effects.put("LIGHTNING", new Lightning());
        effects.put("EXPERIENCE", new Experience());
        effects.put("HEAL", new Heal());
        effects.put("DAMAGE_MODIFIER", new DamageModifier());
        effects.put("DROP_HEAD", new DropHead());
        effects.put("MULTISHOT", new Multishot());
        effects.put("REPLACE_NEAR", new ReplaceNear());
        effects.put("COAT", new Coat());
        effects.put("TELEPATHY", new Telepathy());
    }

    public void registerConditions() {
        conditions = new HashMap<>();
        conditions.put("PLAYER_HEALTH", new PlayerHealth());
        conditions.put("TARGET_HEALTH", new TargetHealth());
        conditions.put("ISWEARING", new IsWearing());
        conditions.put("ISHOLDING", new IsHolding());
    }

    public static IridiumEnchants getInstance() {
        return instance;
    }
}
