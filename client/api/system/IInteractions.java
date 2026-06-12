package org.rusherhack.client.api.system;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public interface IInteractions {
   boolean placeBlock(BlockPos var1, InteractionHand var2, boolean var3);

   boolean useBlock(BlockPos var1, InteractionHand var2, boolean var3, boolean var4);

   boolean useBlock(BlockHitResult var1, InteractionHand var2, boolean var3);

   @Nullable
   BlockHitResult getBlockPlaceHitResult(BlockPos var1, boolean var2, boolean var3, double var4);

   @Nullable
   BlockHitResult getBlockHitResult(BlockPos var1, boolean var2, boolean var3, double var4);

   boolean useEntity(Entity var1, InteractionHand var2);
}
