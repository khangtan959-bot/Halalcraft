package com.halalcraft.mod.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import java.util.Set;
import java.util.HashSet;

public class MaterialUtil {
    private static final Set<Item> RAW_MATERIALS = new HashSet<>();

    static {
        // Ores & Raw metals
        RAW_MATERIALS.add(Items.IRON_INGOT);
        RAW_MATERIALS.add(Items.IRON_ORE);
        RAW_MATERIALS.add(Items.RAW_IRON);
        RAW_MATERIALS.add(Items.GOLD_INGOT);
        RAW_MATERIALS.add(Items.GOLD_ORE);
        RAW_MATERIALS.add(Items.RAW_GOLD);
        RAW_MATERIALS.add(Items.DIAMOND);
        RAW_MATERIALS.add(Items.DIAMOND_ORE);
        RAW_MATERIALS.add(Items.DEEPSLATE_DIAMOND_ORE);
        RAW_MATERIALS.add(Items.COAL);
        RAW_MATERIALS.add(Items.COAL_ORE);
        RAW_MATERIALS.add(Items.DEEPSLATE_COAL_ORE);
        RAW_MATERIALS.add(Items.COPPER_INGOT);
        RAW_MATERIALS.add(Items.COPPER_ORE);
        RAW_MATERIALS.add(Items.RAW_COPPER);

        // Blocks
        RAW_MATERIALS.add(Items.IRON_BLOCK);
        RAW_MATERIALS.add(Items.GOLD_BLOCK);
        RAW_MATERIALS.add(Items.DIAMOND_BLOCK);
        RAW_MATERIALS.add(Items.COAL_BLOCK);

        // Wood & Stone
        RAW_MATERIALS.add(Items.COBBLESTONE);
        RAW_MATERIALS.add(Items.STONE);
        // Since there are many wood types, we check tags in the manager
    }

    public static boolean isRawMaterial(Item item) {
        if (RAW_MATERIALS.contains(item)) return true;
        if (item.isIn(ItemTags.LOGS)) return true;
        if (item.isIn(ItemTags.STEMS)) return true;
        return false;
    }
}
