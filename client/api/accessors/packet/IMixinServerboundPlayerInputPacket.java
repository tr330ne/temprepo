package org.rusherhack.client.api.accessors.packet;

import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerboundPlayerInputPacket.class)
public interface IMixinServerboundPlayerInputPacket {
   @Accessor("isShiftKeyDown")
   @Mutable
   void setIsShiftKeyDown(boolean var1);
}
