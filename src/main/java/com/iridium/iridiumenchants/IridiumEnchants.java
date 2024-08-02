package com.iridium.iridiumenchants;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumcore.dependencies.paperlib.PaperLib;
import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumenchants.commands.customenchants.CommandManager;
import com.iridium.iridiumenchants.commands.gkits.GkitsCommandManager;
import com.iridium.iridiumenchants.conditions.*;
import com.iridium.iridiumenchants.configs.*;
import com.iridium.iridiumenchants.effects.*;
import com.iridium.iridiumenchants.listeners.*;
import com.iridium.iridiumenchants.managers.CustomEnchantManager;
import com.iridium.iridiumenchants.managers.GkitsManager;
import com.iridium.iridiumenchants.managers.UserManager;
import com.iridium.iridiumenchants.support.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

@Getter
public class IridiumEnchants extends IridiumCore {

    @Getter
    private static IridiumEnchants instance;

    private CommandManager commandManager;
    private GkitsCommandManager gkitsCommandManager;
    private CustomEnchantManager customEnchantManager;
    private UserManager userManager;
    private GkitsManager gkitsManager;

    private Configuration configuration;
    private Messages messages;
    private Commands commands;
    private CustomEnchants customEnchants;
    private Inventories inventories;
    private GKits gKits;
    private Types types;

    private Map<String, Effect> effects;
    private Map<String, Condition> conditions;

    private List<BuildSupport> buildSupport;
    private List<FriendlySupport> friendlySupport;

    @Override
    public void onEnable() {
        instance = this;
        this.commandManager = new CommandManager("iridiumenchants");
        this.gkitsCommandManager = new GkitsCommandManager("gkits");
        this.customEnchantManager = new CustomEnchantManager();
        this.userManager = new UserManager();
        this.gkitsManager = new GkitsManager();

        if (!PaperLib.isSpigot()) {
            getLogger().warning("CraftBukkit isn't supported, please use spigot or one of its forks");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::saveData, 0L, 6000L);
            this.registerListeners();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                Bukkit.getServer().getOnlinePlayers().forEach((player) -> {
                    InventoryHolder inventoryHolder = player.getOpenInventory().getTopInventory().getHolder();
                    if (inventoryHolder instanceof GUI) {
                        ((GUI) inventoryHolder).addContent(player.getOpenInventory().getTopInventory());
                    }

                });
            }, 0L, 1L);
        }

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
        for (BlockState blockState : Coat.blockStates.keySet()) {
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
        Bukkit.getPluginManager().registerEvents(new PrepareAnvilListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new EnchantItemListener(), this);
    }

    @Override
    public void loadConfigs() {
        this.configuration = getPersist().load(Configuration.class);
        this.messages = getPersist().load(Messages.class);
        this.commands = getPersist().load(Commands.class);
        this.customEnchants = getPersist().load(CustomEnchants.class);
        this.inventories = getPersist().load(Inventories.class);
        this.gKits = getPersist().load(GKits.class);
        this.types = getPersist().load(Types.class);
        for (Tier tier : configuration.tiers.values()) {
            if (tier.experienceType == null) {
                tier.experienceType = ExperienceType.LEVEL;
            }
        }
    }

    @Override
    public void saveConfigs() {
        getPersist().save(configuration);
        getPersist().save(messages);
        getPersist().save(commands);
        getPersist().save(customEnchants);
        getPersist().save(inventories);
        getPersist().save(gKits);
        getPersist().save(types);
    }

    @Override
    public void saveData() {
        super.saveData();
    }

    public void registerSupport() {
        this.friendlySupport = new ArrayList<>();
        this.buildSupport = new ArrayList<>();
        Arrays.asList(
                new ASkyblockSupportHolder(),
                new FactionsSupportHolder(),
                new FactionsUUIDSupportHolder(),
                new IridiumSkyblockSupportHolder(),
                new LandsSupportHolder(),
                new TownySupportHolder(),
                new WorldGuard7SupportHolder()
        ).forEach(supportHolder -> {
            if (supportHolder.isInstalled()) {
                if (supportHolder instanceof FriendlySupportHolder) {
                    friendlySupport.add(((FriendlySupportHolder) supportHolder).friendlySupport().get());
                }
                buildSupport.add(supportHolder.buildSupport().get());
            }
        });
    }

    public boolean isFriendly(LivingEntity livingEntity, LivingEntity livingEntity2) {
        for (FriendlySupport friendlySupport : friendlySupport) {
            if (friendlySupport.isFriendly(livingEntity, livingEntity2)) return true;
        }
        return false;
    }

    public boolean canBuild(Player player, Location location) {
        for (BuildSupport buildSupport : buildSupport) {
            if (!buildSupport.canBuild(player, location)) return false;
        }
        return true;
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
        effects.put("ADD_EXTRA_HEALTH", new AddExtraHealth());
        effects.put("REMOVE_EXTRA_HEALTH", new RemoveExtraHealth());
    }

    public void registerConditions() {
        conditions = new HashMap<>();
        conditions.put("PLAYER_HEALTH", new PlayerHealth());
        conditions.put("TARGET_HEALTH", new TargetHealth());
        conditions.put("ISWEARING", new IsWearing());
        conditions.put("ISHOLDING", new IsHolding());
        conditions.put("ISSNEAKING", new IsSneaking());
        conditions.put("ENTITY_TYPE", new EntityType());
    }

    public Map<String, CustomEnchant> getCustomEnchantments(){
        HashMap<String, CustomEnchant> customEnchants = new HashMap<>(getCustomEnchants().customEnchants);
        getCustomEnchants().customEnchants.forEach((s, customEnchant) -> {
            if(!customEnchant.enabled) customEnchants.remove(s);
        });

        return customEnchants;
    }

}
