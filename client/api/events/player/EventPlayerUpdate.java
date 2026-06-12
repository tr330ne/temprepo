package org.rusherhack.client.api.events.player;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public class EventPlayerUpdate extends EventCancellable {
   private final LocalPlayer player;
   private float yaw;
   private float pitch;
   private double x;
   private double y;
   private double z;
   private boolean onGround;

   public EventPlayerUpdate(LocalPlayer player) {
      this.player = player;
      Vec3 position = player.position();
      this.yaw = player.getYRot();
      this.pitch = player.getXRot();
      this.x = position.x;
      this.y = position.y;
      this.z = position.z;
      this.onGround = player.onGround();
   }

   public LocalPlayer getPlayer() {
      return this.player;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void setOnGround(boolean onGround) {
      this.onGround = onGround;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.PRE;
   }
}
