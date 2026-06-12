package org.rusherhack.client.api.accessors.world;

import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientChunkCache.Storage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientChunkCache.class)
public interface IMixinClientChunkCache {
   @Accessor("storage")
   Storage getStorage();
}
