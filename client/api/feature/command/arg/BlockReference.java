package org.rusherhack.client.api.feature.command.arg;

import net.minecraft.world.level.block.Block;

public record BlockReference(Block[] blocks, String description) {
   public BlockReference(Block block) {
      this(new Block[]{block}, block.getName().getString());
   }
}
