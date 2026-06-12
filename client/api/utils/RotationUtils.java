package org.rusherhack.client.api.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.rusherhack.client.api.Globals;

public class RotationUtils implements Globals {
   public static float[] getRotations(Vec3 vec) {
      return getRotations(mc.player.getEyePosition(), vec);
   }

   public static float[] getRotations(Vec3 from, Vec3 to) {
      float playerYaw = mc.player.getYRot();
      float playerPitch = mc.player.getXRot();
      float diffX = (float)(to.x - from.x);
      float diffY = (float)(to.y - from.y);
      float diffZ = (float)(to.z - from.z);
      float dist = Mth.sqrt(diffX * diffX + diffZ * diffZ);
      float yaw = (float)Math.toDegrees(Mth.atan2(diffZ, diffX)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Mth.atan2(diffY, dist)));
      return new float[]{playerYaw + Mth.wrapDegrees(yaw - playerYaw), playerPitch + Mth.wrapDegrees(pitch - playerPitch)};
   }
}
