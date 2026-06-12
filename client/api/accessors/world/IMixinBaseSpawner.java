package org.rusherhack.client.api.accessors.world;

import net.minecraft.world.level.BaseSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BaseSpawner.class)
public interface IMixinBaseSpawner {
   @Accessor("spawnDelay")
   int getSpawnDelay();
}
