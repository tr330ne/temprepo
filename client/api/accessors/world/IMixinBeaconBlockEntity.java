package org.rusherhack.client.api.accessors.world;

import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BeaconBlockEntity.class)
public interface IMixinBeaconBlockEntity {
   @Accessor("levels")
   int getLevels();
}
