package org.rusherhack.client.api.accessors.packet;

import net.minecraft.network.ConnectionProtocol;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ConnectionProtocol.class)
public interface IMixinConnectionProtocol {
}
