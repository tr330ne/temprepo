package org.rusherhack.client.api.accessors.packet;

import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundExplodePacket.class)
public interface IMixinClientboundExplodePacket {
   @Accessor("knockbackX")
   @Mutable
   void setKnockbackX(float var1);

   @Accessor("knockbackY")
   @Mutable
   void setKnockbackY(float var1);

   @Accessor("knockbackZ")
   @Mutable
   void setKnockbackZ(float var1);
}
