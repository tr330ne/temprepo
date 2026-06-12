package org.rusherhack.client.api.accessors.world;

import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainer.Data;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PalettedContainer.class)
public interface IMixinPalettedContainer {
   @Accessor("data")
   Data<?> getData();
}
