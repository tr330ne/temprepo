package org.rusherhack.client.api.accessors.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Connection.class)
public interface IMixinConnection {
   @Accessor("channel")
   Channel getChannel();

   @Invoker("doSendPacket")
   void invokeDoSendPacket(Packet<?> var1, @Nullable PacketSendListener var2, boolean var3);

   @Invoker("channelRead0")
   void invokeChannelRead0(ChannelHandlerContext var1, Packet<?> var2);

   @Invoker("genericsFtw")
   static <T extends PacketListener> void invokeGenericsFtw(Packet<T> packet, PacketListener listener) {
   }
}
