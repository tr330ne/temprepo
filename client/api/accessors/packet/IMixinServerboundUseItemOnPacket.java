package org.rusherhack.client.api.accessors.packet;

import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerboundUseItemOnPacket.class)
public interface IMixinServerboundUseItemOnPacket {
   @Accessor("blockHit")
   @Mutable
   void setBlockHit(BlockHitResult var1);

   @Accessor("hand")
   @Mutable
   void setHand(InteractionHand var1);
}
