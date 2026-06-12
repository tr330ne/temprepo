package org.rusherhack.client.api.accessors.packet;

import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerboundMovePlayerPacket.class)
public interface IMixinServerboundMovePlayerPacket {
   @Accessor("x")
   @Mutable
   void setX(double var1);

   @Accessor("y")
   @Mutable
   void setY(double var1);

   @Accessor("z")
   @Mutable
   void setZ(double var1);

   @Accessor("yRot")
   @Mutable
   void setYaw(float var1);

   @Accessor("xRot")
   @Mutable
   void setPitch(float var1);

   @Accessor("onGround")
   @Mutable
   void setOnGround(boolean var1);
}
