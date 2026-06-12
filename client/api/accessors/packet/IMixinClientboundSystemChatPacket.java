package org.rusherhack.client.api.accessors.packet;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundSystemChatPacket.class)
public interface IMixinClientboundSystemChatPacket {
   @Accessor("content")
   @Mutable
   void setContent(Component var1);
}
