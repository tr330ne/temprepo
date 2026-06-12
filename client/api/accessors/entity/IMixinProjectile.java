package org.rusherhack.client.api.accessors.entity;

import java.util.UUID;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Projectile.class)
public interface IMixinProjectile {
   @Accessor("ownerUUID")
   UUID getOwnerUUID();
}
