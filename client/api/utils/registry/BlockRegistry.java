package org.rusherhack.client.api.utils.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SignBlock;

public class BlockRegistry {
   public static final Map<String, Block> BLOCKS = new HashMap<>();
   public static final HashMap<String[], Block[]> WILD_CARDS = new HashMap<>();

   public static Block[] getBlocks(String query) {
      query = query.toLowerCase();
      if (query.startsWith("minecraft:")) {
         query = query.substring("minecraft:".length());
      }

      if (BLOCKS.containsKey(query)) {
         return new Block[]{BLOCKS.get(query)};
      }

      for (Entry<String[], Block[]> entry : WILD_CARDS.entrySet()) {
         for (String alias : entry.getKey()) {
            if (query.equalsIgnoreCase(alias)) {
               return entry.getValue();
            }
         }
      }

      return new Block[0];
   }

   public static Block[] findBlocks(String query) {
      query = query.toLowerCase();
      if (query.startsWith("minecraft:")) {
         query = query.substring("minecraft:".length());
      }

      HashSet<Block> blocks = new HashSet<>();

      for (Entry<String, Block> entry : BLOCKS.entrySet()) {
         String key = entry.getKey();
         if (key.contains(query)) {
            blocks.add(entry.getValue());
         }
      }

      for (Entry<String[], Block[]> entry : WILD_CARDS.entrySet()) {
         for (String alias : entry.getKey()) {
            if (query.contains(alias)) {
               blocks.addAll(Arrays.asList(entry.getValue()));
            }
         }
      }

      return blocks.toArray(new Block[0]);
   }

   public static String getID(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block).getPath();
   }

   static {
      List<Block> allWood = new ArrayList<>();
      List<Block> logs = new ArrayList<>();
      List<Block> planks = new ArrayList<>();
      List<Block> saplings = new ArrayList<>();
      List<Block> ores = new ArrayList<>();
      List<Block> wool = new ArrayList<>();
      List<Block> terracotta = new ArrayList<>();
      List<Block> chests = new ArrayList<>();
      List<Block> shulkers = new ArrayList<>();
      List<Block> anvils = new ArrayList<>();
      List<Block> beds = new ArrayList<>();
      List<Block> signs = new ArrayList<>();
      List<Block> banners = new ArrayList<>();
      List<Block> heads = new ArrayList<>();

      for (Block block : BuiltInRegistries.BLOCK) {
         if (block instanceof ChestBlock) {
            chests.add(block);
         } else if (block instanceof ShulkerBoxBlock) {
            shulkers.add(block);
         } else if (block instanceof AnvilBlock) {
            anvils.add(block);
         } else if (block instanceof BedBlock) {
            beds.add(block);
         } else if (block instanceof SignBlock) {
            signs.add(block);
         } else if (block instanceof AbstractBannerBlock) {
            banners.add(block);
         } else if (block instanceof AbstractSkullBlock) {
            heads.add(block);
         }

         if (block.getDescriptionId().endsWith("_ore")) {
            ores.add(block);
         }

         if (block.getDescriptionId().endsWith("_wood")) {
            allWood.add(block);
         }

         if (block.getDescriptionId().endsWith("_log")) {
            logs.add(block);
            allWood.add(block);
         }

         if (block.getDescriptionId().endsWith("_planks")) {
            planks.add(block);
            allWood.add(block);
         }

         if (block.getDescriptionId().endsWith("_sapling")) {
            saplings.add(block);
         }

         if (block.getDescriptionId().endsWith("_wool")) {
            wool.add(block);
         }

         if (block.getDescriptionId().endsWith("_terracotta")) {
            terracotta.add(block);
         }

         BLOCKS.put(BuiltInRegistries.BLOCK.getKey(block).getPath(), block);
      }

      WILD_CARDS.put(new String[]{"wood"}, allWood.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"wooden_logs"}, logs.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"wooden_planks"}, planks.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"saplings"}, saplings.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"ores"}, ores.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"wool"}, wool.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"terracotta"}, terracotta.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"shulkerboxes", "shulkers", "shulker", "shulkerbox"}, shulkers.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"chests"}, chests.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"anvils"}, anvils.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"beds", "bed"}, beds.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"signs", "sign"}, signs.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"banners", "banner"}, banners.toArray(new Block[0]));
      WILD_CARDS.put(new String[]{"heads", "head", "skulls", "skull"}, heads.toArray(new Block[0]));
   }
}
