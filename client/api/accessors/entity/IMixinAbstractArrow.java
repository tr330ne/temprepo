package org.rusherhack.client.api.accessors.entity;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractArrow.class)
public interface IMixinAbstractArrow {
   @Accessor("inGround")
   boolean isInGround();
}
