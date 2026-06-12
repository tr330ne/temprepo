package org.rusherhack.client.api.accessors.entity;

import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FishingHook.class)
public interface IMixinFishingHook {
   @Accessor("biting")
   boolean isBiting();
}
