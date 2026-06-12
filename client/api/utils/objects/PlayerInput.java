package org.rusherhack.client.api.utils.objects;

import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec2;

public record PlayerInput(boolean forward, boolean backwards, boolean left, boolean right, boolean jumping, boolean sneaking) {
   public PlayerInput(LocalPlayer player) {
      this(player.input);
   }

   public PlayerInput(Input input) {
      this(input.up, input.down, input.left, input.right, input.jumping, input.shiftKeyDown);
   }

   public float x() {
      float impulse = 0.0F;
      if (this.left()) {
         impulse++;
      }

      if (this.right()) {
         impulse--;
      }

      return impulse;
   }

   public float y() {
      float impulse = 0.0F;
      if (this.forward()) {
         impulse++;
      }

      if (this.backwards()) {
         impulse--;
      }

      return impulse;
   }

   public Vec2 horizontalVector() {
      return new Vec2(this.x(), this.y());
   }

   public boolean hasHorizontalInput() {
      return this.x() != 0.0F || this.y() != 0.0F;
   }

   public static class Builder {
      private boolean forward = false;
      private boolean backwards = false;
      private boolean left = false;
      private boolean right = false;
      private boolean jumping = false;
      private boolean sneaking = false;

      public Builder(LocalPlayer player) {
         this(player.input);
      }

      public Builder(Input input) {
         this.forward = input.up;
         this.backwards = input.down;
         this.left = input.left;
         this.right = input.right;
         this.jumping = input.jumping;
         this.sneaking = input.shiftKeyDown;
      }

      public Builder() {
      }

      public PlayerInput build() {
         return new PlayerInput(this.forward, this.backwards, this.left, this.right, this.jumping, this.sneaking);
      }

      public boolean forward() {
         return this.forward;
      }

      public PlayerInput.Builder forward(boolean forward) {
         this.forward = forward;
         return this;
      }

      public boolean backwards() {
         return this.backwards;
      }

      public PlayerInput.Builder backwards(boolean backwards) {
         this.backwards = backwards;
         return this;
      }

      public boolean left() {
         return this.left;
      }

      public PlayerInput.Builder left(boolean left) {
         this.left = left;
         return this;
      }

      public boolean right() {
         return this.right;
      }

      public PlayerInput.Builder right(boolean right) {
         this.right = right;
         return this;
      }

      public boolean jump() {
         return this.jumping;
      }

      public PlayerInput.Builder jump(boolean jump) {
         this.jumping = jump;
         return this;
      }

      public boolean sneak() {
         return this.sneaking;
      }

      public PlayerInput.Builder sneak(boolean sneak) {
         this.sneaking = sneak;
         return this;
      }

      public void release() {
         this.forward(false).backwards(false).left(false).right(false).jump(false).sneak(false);
      }

      public float x() {
         float impulse = 0.0F;
         if (this.left()) {
            impulse++;
         }

         if (this.right()) {
            impulse--;
         }

         return impulse;
      }

      public float y() {
         float impulse = 0.0F;
         if (this.forward()) {
            impulse++;
         }

         if (this.backwards()) {
            impulse--;
         }

         return impulse;
      }

      public Vec2 movementVector() {
         return new Vec2(this.x(), this.y());
      }
   }
}
