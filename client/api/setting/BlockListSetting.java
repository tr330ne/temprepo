package org.rusherhack.client.api.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.world.level.block.Block;
import org.rusherhack.client.api.feature.command.arg.BlockReference;
import org.rusherhack.client.api.utils.registry.BlockRegistry;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;
import org.rusherhack.core.setting.ListSetting;

public class BlockListSetting extends ListSetting<Block> {
   public BlockListSetting(String name, Block... defaultItems) {
      super(name, "", new HashSet<>(List.of(defaultItems)));
   }

   public BlockListSetting(String name, String description, Block... defaultItems) {
      super(name, description, new HashSet<>(List.of(defaultItems)));
   }

   public BlockListSetting(String name, String description, boolean toggleable, Block... defaultItems) {
      super(name, description, toggleable, new HashSet<>(List.of(defaultItems)));
   }

   public JsonElement serializeElement(Block block) {
      return new JsonPrimitive(BlockRegistry.getID(block));
   }

   public Block deserializeElement(JsonElement element) {
      return element instanceof JsonPrimitive primitive && primitive.isString() ? this.parseElement(primitive.getAsString()) : null;
   }

   public Block parseElement(String string) {
      Block[] blocks = BlockRegistry.getBlocks(string);
      return blocks.length == 0 ? null : blocks[0];
   }

   public String getElementDisplayName(Block block) {
      return block.getName().getString();
   }

   public String[] getElementAttributes(Block element) {
      return element == null ? new String[]{"Block"} : new String[]{this.getElementDisplayName(element)};
   }

   @Override
   public AbstractCommand createCommand(AbstractCommand parent) {
      return super.createCommand(parent);
   }

   @Override
   public Collection<Block> getPossibleElements() {
      return BlockRegistry.BLOCKS.values();
   }

   @Override
   protected AbstractCommand createAddCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "add", "Adds a block to the list") {
         @CommandExecutor
         @CommandExecutor.Argument("block")
         public String addBlock(BlockReference blockReference) {
            String blockName = blockReference.description();
            BlockListSetting.this.addAll(blockReference.blocks());
            return String.format("Added block %s to the %s list", blockName, BlockListSetting.this.getDisplayString());
         }
      };
   }

   @Override
   protected AbstractCommand createRemoveCommand(AbstractCommand parent) {
      AbstractCommand removeCommand = new AbstractCommand(parent, "remove", "Removes a block from the list") {
         @CommandExecutor
         @CommandExecutor.Argument("block")
         public String removeBlock(BlockReference blockReference) {
            String blockName = blockReference.description();
            boolean removed = BlockListSetting.this.removeAll(blockReference.blocks());
            return removed
               ? String.format("Removed block %s from the %s list", blockName, BlockListSetting.this.getDisplayString())
               : String.format("%s was not found in the %s list", blockName, BlockListSetting.this.getDisplayString());
         }
      };
      removeCommand.addAliases("rm", "delete", "del");
      return removeCommand;
   }

   @Override
   protected AbstractCommand createListCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "list", "List the blocks in the list") {
         @CommandExecutor
         private Component listElements() {
            MutableComponent delimiter = Component.literal(", ");
            ArrayList<Block> blocks = new ArrayList<>(BlockListSetting.this.getList());
            blocks.sort(Comparator.comparing(BlockListSetting.this::getElementDisplayName));
            MutableComponent blocksComponent = Component.literal(String.format("%s {%s}: ", BlockListSetting.this.getDisplayString(), blocks.size()));

            for (int i = 0; i < blocks.size(); i++) {
               Block block = blocks.get(i);
               if (i != 0) {
                  blocksComponent.append(delimiter);
               }

               HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, Component.literal(block.getDescriptionId()));
               Style style = Style.EMPTY.withHoverEvent(hoverEvent).withColor(ChatFormatting.GREEN);
               MutableComponent blockComponent = Component.literal(BlockListSetting.this.getElementDisplayName(block)).withStyle(style);
               blocksComponent.append(blockComponent);
            }

            return blocksComponent;
         }
      };
   }
}
