package org.rusherhack.client.api.accessors.packet;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.LastSeenMessagesTracker;
import net.minecraft.network.chat.SignedMessageChain.Encoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPacketListener.class)
public interface IMixinClientPacketListener {
   @Accessor("lastSeenMessages")
   LastSeenMessagesTracker getLastSeenMessages();

   @Accessor("signedMessageEncoder")
   Encoder getSignedMessageEncoder();

   @Accessor("playerInfoMap")
   Map<UUID, PlayerInfo> getPlayerInfoMap();
}
