package org.rusherhack.client.api.utils;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.accessors.entity.IMixinEntity;
import org.rusherhack.client.api.utils.objects.PlayerInput;

public class PlayerUtils implements Globals {
   public static double getCurrentSpeed() {
      if (mc.player == null) {
         return 0.0;
      }

      Entity entity = (Entity)(mc.player.getVehicle() != null ? mc.player.getVehicle() : mc.player);
      double deltaX = entity.getX() - entity.xOld;
      double deltaZ = entity.getZ() - entity.zOld;
      return Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
   }

   public static double[] getDirectionalSpeed(double speed) {
      return getDirectionalSpeed(mc.player, mc.player.getYRot(), speed);
   }

   public static double[] getDirectionalSpeed(float yaw, double speed) {
      return getDirectionalSpeed(mc.player, yaw, speed);
   }

   public static double[] getDirectionalSpeed(LocalPlayer player, float yaw, double speed) {
      PlayerInput input = getInput(player);
      return getDirectionalSpeed(player, yaw, speed, input.y(), input.x());
   }

   public static double[] getDirectionalSpeed(LocalPlayer player, float yaw, double speed, float forward, float strafe) {
      if (player == null) {
         return new double[]{0.0, 0.0};
      }

      if (forward != 0.0F) {
         if (strafe > 0.0F) {
            yaw += forward > 0.0F ? -45 : 45;
         } else if (strafe < 0.0F) {
            yaw += forward > 0.0F ? 45 : -45;
         }

         strafe = 0.0F;
         forward = forward > 0.0F ? 1.0F : -1.0F;
      }

      double rotationRad = Math.toRadians(yaw + 90.0F);
      double cos = Math.cos(rotationRad);
      double sin = Math.sin(rotationRad);
      double posX = forward * speed * cos + strafe * speed * sin;
      double posZ = forward * speed * sin - strafe * speed * cos;
      return new double[]{posX, posZ};
   }

   public static PlayerInput getInput() {
      return new PlayerInput(mc.player);
   }

   public static PlayerInput getInput(LocalPlayer player) {
      return player == null ? null : new PlayerInput(player);
   }

   @Deprecated(forRemoval = true)
   public static boolean hasHorizontalInput() {
      PlayerInput input = getInput();
      return input == null ? false : input.hasHorizontalInput();
   }

   public static float getFallDistance() {
      return mc.player.fallDistance;
   }

   public static void setFallDistance(float distance) {
      mc.player.fallDistance = distance;
   }

   public static BlockPos getEyeBlockPos() {
      return new BlockPos(mc.player.getBlockX(), Mth.floor(mc.player.getEyeY()), mc.player.getBlockZ());
   }

   public static boolean isStuck() {
      return !((IMixinEntity)mc.player).getStuckSpeedMultiplier().equals(Vec3.ZERO);
   }

   public static List<ItemStack> getArmorStacks() {
      return getArmorStacks(mc.player);
   }

   public static List<ItemStack> getArmorStacks(LivingEntity entity) {
      return entity == null
         ? List.of()
         : Lists.newArrayList(
            new ItemStack[]{
               entity.getItemBySlot(EquipmentSlot.HEAD),
               entity.getItemBySlot(EquipmentSlot.CHEST),
               entity.getItemBySlot(EquipmentSlot.LEGS),
               entity.getItemBySlot(EquipmentSlot.FEET)
            }
         );
   }

   public static double getBlockReach() {
      return mc.player.blockInteractionRange();
   }

   public static double getEntityReach() {
      return mc.player.entityInteractionRange();
   }
}
