package com.iridium.iridiumenchants;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum Type {
    @Deprecated
    ALL {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            for (Type target : Type.values()) {
                if (target != this && target.includes(item)) {
                    return true;
                }
            }

            return false;
        }
    },

    /**
     * Allows the Enchantment to be placed on armor
     */
    ARMOR {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return BOOTS.includes(item)
                    || LEGGINGS.includes(item)
                    || HELMET.includes(item)
                    || CHESTPLATE.includes(item);
        }
    },

    /**
     * Allows the Enchantment to be placed on feet slot armor
     */
    BOOTS {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.LEATHER_BOOTS)
                    || item.equals(XMaterial.CHAINMAIL_BOOTS)
                    || item.equals(XMaterial.IRON_BOOTS)
                    || item.equals(XMaterial.DIAMOND_BOOTS)
                    || item.equals(XMaterial.GOLDEN_BOOTS)
                    || item.equals(XMaterial.NETHERITE_BOOTS);
        }
    },

    /**
     * Allows the Enchantment to be placed on leg slot armor
     */
    LEGGINGS {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.LEATHER_LEGGINGS)
                    || item.equals(XMaterial.CHAINMAIL_LEGGINGS)
                    || item.equals(XMaterial.IRON_LEGGINGS)
                    || item.equals(XMaterial.DIAMOND_LEGGINGS)
                    || item.equals(XMaterial.GOLDEN_LEGGINGS)
                    || item.equals(XMaterial.NETHERITE_LEGGINGS);
        }
    },

    /**
     * Allows the Enchantment to be placed on torso slot armor
     */
    CHESTPLATE {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.LEATHER_CHESTPLATE)
                    || item.equals(XMaterial.CHAINMAIL_CHESTPLATE)
                    || item.equals(XMaterial.IRON_CHESTPLATE)
                    || item.equals(XMaterial.DIAMOND_CHESTPLATE)
                    || item.equals(XMaterial.GOLDEN_CHESTPLATE)
                    || item.equals(XMaterial.NETHERITE_CHESTPLATE);
        }
    },

    /**
     * Allows the Enchantment to be placed on head slot armor
     */
    HELMET {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.LEATHER_HELMET)
                    || item.equals(XMaterial.CHAINMAIL_HELMET)
                    || item.equals(XMaterial.DIAMOND_HELMET)
                    || item.equals(XMaterial.IRON_HELMET)
                    || item.equals(XMaterial.GOLDEN_HELMET)
                    || item.equals(XMaterial.TURTLE_HELMET)
                    || item.equals(XMaterial.NETHERITE_HELMET);
        }
    },

    /**
     * Allows the Enchantment to be placed on swords
     */
    SWORD {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.WOODEN_SWORD)
                    || item.equals(XMaterial.STONE_SWORD)
                    || item.equals(XMaterial.IRON_SWORD)
                    || item.equals(XMaterial.DIAMOND_SWORD)
                    || item.equals(XMaterial.GOLDEN_SWORD)
                    || item.equals(XMaterial.NETHERITE_SWORD);
        }
    },

    /**
     * Allows the Enchantment to be placed on axes
     */
    AXE {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.WOODEN_AXE)
                    || item.equals(XMaterial.STONE_AXE)
                    || item.equals(XMaterial.IRON_AXE)
                    || item.equals(XMaterial.DIAMOND_AXE)
                    || item.equals(XMaterial.GOLDEN_AXE)
                    || item.equals(XMaterial.NETHERITE_AXE);
        }
    },

    /**
     * Allows the Enchantment to be placed on bows.
     */
    BOW {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.BOW);
        }
    },

    /**
     * Allow the Enchantment to be placed on tridents.
     */
    TRIDENT {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.TRIDENT);
        }
    },

    /**
     * Allow the Enchantment to be placed on crossbows.
     */
    CROSSBOW {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.CROSSBOW);
        }
    },

    /**
     * Allows the Enchantment to be placed on axes and swords
     */
    MELEE_WEAPON {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return SWORD.includes(item) || AXE.includes(item);
        }
    },

    /**
     * Allows the Enchantment to be placed on bows crossbows and tridents
     */
    RANGED_WEAPON {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return BOW.includes(item) || CROSSBOW.includes(item) || TRIDENT.includes(item);
        }
    },

    /**
     * Allows the Enchantment to be placed on all melee and ranged weapons
     */
    WEAPON {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return MELEE_WEAPON.includes(item) || RANGED_WEAPON.includes(item);
        }
    },

    /**
     * Allows the Enchantment to be placed on pickaxes
     */
    PICKAXE {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.WOODEN_PICKAXE)
                    || item.equals(XMaterial.STONE_PICKAXE)
                    || item.equals(XMaterial.IRON_PICKAXE)
                    || item.equals(XMaterial.DIAMOND_PICKAXE)
                    || item.equals(XMaterial.GOLDEN_PICKAXE)
                    || item.equals(XMaterial.NETHERITE_PICKAXE);
        }
    },

    /**
     * Allows the Enchantment to be placed on shovels
     */
    SHOVEL {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.WOODEN_SHOVEL)
                    || item.equals(XMaterial.STONE_SHOVEL)
                    || item.equals(XMaterial.IRON_SHOVEL)
                    || item.equals(XMaterial.DIAMOND_SHOVEL)
                    || item.equals(XMaterial.GOLDEN_SHOVEL)
                    || item.equals(XMaterial.NETHERITE_SHOVEL);
        }
    },

    /**
     * Allows the Enchantment to be placed on hoe's
     */
    HOE {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.WOODEN_HOE)
                    || item.equals(XMaterial.STONE_HOE)
                    || item.equals(XMaterial.IRON_HOE)
                    || item.equals(XMaterial.DIAMOND_HOE)
                    || item.equals(XMaterial.GOLDEN_HOE)
                    || item.equals(XMaterial.NETHERITE_HOE);
        }
    },

    /**
     * Allows the Enchantment to be placed on tools (spades, pickaxe, axes and hoe's)
     */
    TOOL {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return PICKAXE.includes(item) || AXE.includes(item) || HOE.includes(item) || SHOVEL.includes(item);
        }
    },

    /**
     * Allows the Enchantment to be placed on fishing rods.
     */
    FISHING_ROD {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return item.equals(XMaterial.FISHING_ROD);
        }
    },

    /**
     * Allows the enchantment to be placed on wearable items.
     */
    WEARABLE {
        @Override
        public boolean includes(@NotNull XMaterial item) {
            return ARMOR.includes(item)
                    || item.equals(XMaterial.ELYTRA)
                    || item.equals(XMaterial.CARVED_PUMPKIN)
                    || item.equals(XMaterial.JACK_O_LANTERN)
                    || item.equals(XMaterial.SKELETON_SKULL)
                    || item.equals(XMaterial.WITHER_SKELETON_SKULL)
                    || item.equals(XMaterial.ZOMBIE_HEAD)
                    || item.equals(XMaterial.PLAYER_HEAD)
                    || item.equals(XMaterial.CREEPER_HEAD)
                    || item.equals(XMaterial.DRAGON_HEAD);
        }
    };

    /**
     * Check whether this target includes the specified item.
     *
     * @param item The item to check
     * @return True if the target includes the item
     */
    public abstract boolean includes(@NotNull XMaterial item);

    /**
     * Check whether this target includes the specified item.
     *
     * @param item The item to check
     * @return True if the target includes the item
     */
    public boolean includes(@NotNull ItemStack item) {
        return includes(XMaterial.matchXMaterial(item));
    }
}
