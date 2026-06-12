package org.rusherhack.client.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.rusherhack.client.api.Globals;
import org.rusherhack.core.utils.MathUtils;

public class EntityUtils implements Globals {
   public static Vec3 interpolateEntityVec(Entity entity, float partialTicks) {
      double[] pos = interpolateEntityPosition(entity, partialTicks);
      return new Vec3(pos[0], pos[1], pos[2]);
   }

   public static double[] interpolateEntityPosition(Entity entity, float partialTicks) {
      if (entity.tickCount == 0) {
         return new double[]{entity.getX(), entity.getY(), entity.getZ()};
      }

      double x = MathUtils.interpolate(entity.getX(), entity.xo, partialTicks);
      double y = MathUtils.interpolate(entity.getY(), entity.yo, partialTicks);
      double z = MathUtils.interpolate(entity.getZ(), entity.zo, partialTicks);
      return new double[]{x, y, z};
   }

   public static String getNameForEntity(Entity entity) {
      if (entity instanceof ItemEntity item) {
         String itemName = entity.getName().getString();
         int stackSize = item.getItem().getCount();
         if (stackSize > 1) {
            itemName = itemName + " x" + stackSize;
         }

         return itemName;
      } else {
         return entity.getName().getString();
      }
   }
}
