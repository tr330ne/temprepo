package org.rusherhack.client.api.events.player;

import net.minecraft.world.phys.Vec2;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;

public class EventTravel extends Event {
   private final EventTravel.Action action;
   private float yaw;
   private float pitch;

   public EventTravel(Vec2 rotationVector, EventTravel.Action action) {
      this.yaw = rotationVector.y;
      this.pitch = rotationVector.x;
      this.action = action;
   }

   public Vec2 getRotationVector() {
      return new Vec2(this.pitch, this.yaw);
   }

   public EventTravel.Action getAction() {
      return this.action;
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

   @Override
   public Stage getPreferredStage() {
      return Stage.PRE;
   }

   public enum Action {
      MOVE,
      JUMP;
   }
}
