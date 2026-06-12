package org.rusherhack.client.api.system;

import net.minecraft.world.entity.Entity;

public interface Entities {
   boolean isPassiveMob(Entity var1);

   boolean isNeutralMob(Entity var1);

   boolean isHostileMob(Entity var1);

   boolean isProjectileEntity(Entity var1);

   boolean isVehicleEntity(Entity var1);
}
