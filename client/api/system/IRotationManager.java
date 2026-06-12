package org.rusherhack.client.api.system;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;

public interface IRotationManager {
   default void updateRotation(Entity entity) {
      this.updateRotation(entity, 180.0F);
   }

   void updateRotation(Entity var1, float var2);

   default void updateRotation(BlockPos blockPos) {
      this.updateRotation(blockPos, 180.0F);
   }

   default void updateRotation(BlockPos blockPos, float step) {
      this.updateRotation(blockPos, null, step);
   }

   void updateRotation(BlockPos var1, Direction var2, float var3);

   void updateRotation(BlockHitResult var1, float var2);

   default void updateRotation(float yaw, float pitch) {
      this.updateRotation(yaw, pitch, 180.0F);
   }

   void updateRotation(float var1, float var2, float var3);

   boolean isLookingAt(Entity var1);

   default boolean isLookingAt(BlockHitResult hitResult) {
      return this.isLookingAt(hitResult, true);
   }

   default boolean isLookingAt(BlockHitResult hitResult, boolean checkDirection) {
      return this.isLookingAt(hitResult.getBlockPos(), checkDirection ? hitResult.getDirection() : null);
   }

   default boolean isLookingAt(BlockPos blockPos) {
      return this.isLookingAt(blockPos, null);
   }

   boolean isLookingAt(BlockPos var1, Direction var2);

   BlockHitResult getLookRaycast(BlockPos var1);
}
