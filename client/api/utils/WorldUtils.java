package org.rusherhack.client.api.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Predicate;
import net.minecraft.client.multiplayer.ClientChunkCache.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.accessors.world.IMixinClientChunkCache;
import org.rusherhack.client.api.accessors.world.IMixinLevel;

public class WorldUtils implements Globals {
   public static int getMaxY() {
      return mc.level.getMaxBuildHeight();
   }

   public static int getMinY() {
      return mc.level.getMinBuildHeight();
   }

   public static List<Entity> getEntities() {
      return getEntities(null);
   }

   public static List<Entity> getEntities(Predicate<Entity> predicate) {
      if (mc.level == null) {
         return new ArrayList<>();
      }

      List<Entity> entities = new ArrayList<>();

      for (Entity entity : ((IMixinLevel)mc.level).getEntities().getAll()) {
         if (entity != null && (predicate == null || predicate.test(entity))) {
            entities.add(entity);
         }
      }

      return entities;
   }

   public static List<Entity> getEntitiesSorted() {
      return getEntitiesSorted(mc.getCameraEntity());
   }

   public static List<Entity> getEntitiesSorted(Entity entity) {
      return getEntitiesSorted(entity, null);
   }

   public static List<Entity> getEntitiesSorted(Entity entity, Predicate<Entity> predicate) {
      List<Entity> entities = getEntities(predicate);
      entities.sort(Comparator.comparingDouble(e -> e.distanceToSqr(entity)));
      return entities;
   }

   public static List<LevelChunk> getChunks() {
      List<LevelChunk> chunks = new ArrayList<>();
      if (mc.level != null) {
         Storage storage = ((IMixinClientChunkCache)mc.level.getChunkSource()).getStorage();
         AtomicReferenceArray<LevelChunk> chunkArray = storage.chunks;

         for (int i = 0; i < chunkArray.length(); i++) {
            LevelChunk chunk = chunkArray.get(i);
            if (chunk != null) {
               chunks.add(chunk);
            }
         }
      }

      return chunks;
   }

   public static List<BlockEntity> getBlockEntities(boolean sorted) {
      List<BlockEntity> blockEntities = new ArrayList<>();

      for (LevelChunk chunk : getChunks()) {
         for (Entry<BlockPos, BlockEntity> entry : chunk.getBlockEntities().entrySet()) {
            blockEntities.add(entry.getValue());
         }
      }

      if (sorted) {
         blockEntities.sort(Comparator.comparingDouble(be -> be.getBlockPos().getCenter().distanceTo(mc.getCameraEntity().position())));
      }

      return blockEntities;
   }

   public static List<BlockPos> getSphere(Vec3i center, float sphereRadius) {
      return getSphere(center, sphereRadius, null);
   }

   public static List<BlockPos> getSphere(Vec3i center, float sphereRadius, Predicate<BlockPos> predicate) {
      List<BlockPos> spherePositions = new ArrayList<>();
      int radius = Mth.ceil(sphereRadius);
      int centerX = center.getX();
      int centerY = center.getY();
      int centerZ = center.getZ();
      int minY = Math.max(centerY - radius, getMinY());
      int maxY = Math.min(centerY + radius, getMaxY());
      MutableBlockPos pos = new MutableBlockPos();

      for (int x = centerX - radius; x <= centerX + radius; x++) {
         for (int z = centerZ - radius; z <= centerZ + radius; z++) {
            for (int y = minY; y <= maxY; y++) {
               int xSqr = Mth.square(centerX - x);
               int ySqr = Mth.square(centerY - y);
               int zSqr = Mth.square(centerZ - z);
               if (xSqr + ySqr + zSqr <= Mth.square(radius)) {
                  pos.set(x, y, z);
                  if (predicate == null || predicate.test(pos)) {
                     spherePositions.add(pos.immutable());
                  }
               }
            }
         }
      }

      return spherePositions;
   }

   public static List<BlockPos> getCircle(Vec3i center, float circleRadius) {
      List<BlockPos> circlePositions = new ArrayList<>();
      int radius = Mth.ceil(circleRadius);
      int centerX = center.getX();
      int centerZ = center.getZ();

      for (int x = centerX - radius; x <= centerX + radius; x++) {
         for (int z = centerZ - radius; z <= centerZ + radius; z++) {
            int xSqr = Mth.square(centerX - x);
            int zSqr = Mth.square(centerZ - z);
            if (xSqr + zSqr <= Mth.square(radius)) {
               circlePositions.add(new BlockPos(x, center.getY(), z));
            }
         }
      }

      return circlePositions;
   }

   public static boolean isReplaceble(BlockPos pos) {
      return mc.level.getBlockState(pos).canBeReplaced();
   }

   public static boolean checkCollision(BlockPos pos) {
      return checkCollision(pos, Shapes.block(), null);
   }

   public static boolean checkCollision(BlockPos pos, VoxelShape shape, Predicate<Entity> predicate) {
      if (shape.isEmpty()) {
         return true;
      }

      shape = shape.move(pos.getX(), pos.getY(), pos.getZ());

      for (Entity entity : mc.level.getEntities(null, shape.bounds())) {
         if (!entity.isRemoved()
            && entity.blocksBuilding
            && (predicate == null || predicate.test(entity))
            && Shapes.joinIsNotEmpty(shape, Shapes.create(entity.getBoundingBox()), BooleanOp.AND)) {
            return false;
         }
      }

      return true;
   }

   public static BlockPos getBlockPos(Vec3 vec) {
      return new BlockPos(Mth.floor(vec.x), Mth.floor(vec.y), Mth.floor(vec.z));
   }

   public static boolean isThundering() {
      return mc.level.dimensionType().hasSkyLight() && !mc.level.dimensionType().hasCeiling()
         ? ((IMixinLevel)mc.level).getThunderLevel() * ((IMixinLevel)mc.level).getRainLevel() > 0.9
         : false;
   }

   public static boolean isRaining() {
      return ((IMixinLevel)mc.level).getRainLevel() > 0.2;
   }
}
