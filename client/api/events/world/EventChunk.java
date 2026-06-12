package org.rusherhack.client.api.events.world;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;

public abstract class EventChunk extends Event {
   private final ChunkPos pos;

   public EventChunk(ChunkPos pos) {
      this.pos = pos;
   }

   public ChunkPos getChunkPos() {
      return this.pos;
   }

   public static class Load extends EventChunk {
      private final LevelChunk chunk;

      public Load(ChunkPos pos, LevelChunk chunk) {
         super(pos);
         this.chunk = chunk;
      }

      public LevelChunk getChunk() {
         return this.chunk;
      }

      @Override
      public Stage getPreferredStage() {
         return Stage.POST;
      }

      @Override
      public Stage getStage() {
         return super.getStage();
      }
   }

   public static class Unload extends EventChunk {
      public Unload(ChunkPos pos) {
         super(pos);
      }
   }
}
