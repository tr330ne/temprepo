package org.rusherhack.client.api.accessors.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface IMixinEntity {
   @Accessor("onGround")
   void setOnGround(boolean var1);

   @Accessor
   EntityDimensions getDimensions();

   @Accessor
   Vec3 getStuckSpeedMultiplier();

   @Accessor("stuckSpeedMultiplier")
   void setStuckSpeedMultiplier(Vec3 var1);

   @Accessor("wasTouchingWater")
   void setTouchingWater(boolean var1);

   @Invoker("collide")
   Vec3 callCollide(Vec3 var1);

   @Invoker("getSharedFlag")
   boolean invokeGetSharedFlag(int var1);

   @Invoker("setSharedFlag")
   void invokeSetSharedFlag(int var1, boolean var2);

   @Invoker("calculateViewVector")
   Vec3 invokeCalculateViewVector(float var1, float var2);

   @Invoker("canAddPassenger")
   boolean invokeCanAddPassenger(Entity var1);
}
