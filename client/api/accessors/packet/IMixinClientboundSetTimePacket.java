package org.rusherhack.client.api.accessors.packet;

import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundSetTimePacket.class)
public interface IMixinClientboundSetTimePacket {
   @Mutable
   @Accessor("dayTime")
   void setDayTime(long var1);
}
