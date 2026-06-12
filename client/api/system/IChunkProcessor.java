package org.rusherhack.client.api.system;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;

public interface IChunkProcessor {
   void registerProcessee(IChunkProcessor.Processee var1);

   void reloadChunks();

   void scanChunk(ChunkPos var1, Predicate<BlockState> var2, Consumer<HashMap<BlockPos, BlockState>> var3);

   interface Processee {
      void process(ChunkPos var1, boolean var2);

      boolean isActive();
   }
}
