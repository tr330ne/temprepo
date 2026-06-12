package org.rusherhack.client.api.events.player;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public class EventInteract extends EventCancellable {
   private final Entity targetEntity;
   private final InteractionHand hand;
   private final EventInteract.Action action;
   private final EntityHitResult hitResult;
   private boolean usingSecondaryAction;

   public EventInteract(Entity entity, InteractionHand hand, boolean usingSecondaryAction, EventInteract.Action action, EntityHitResult hitResult) {
      this.targetEntity = entity;
      this.hand = hand;
      this.usingSecondaryAction = usingSecondaryAction;
      this.action = action;
      this.hitResult = hitResult;
   }

   public Entity getTargetEntity() {
      return this.targetEntity;
   }

   public InteractionHand getHand() {
      return this.hand;
   }

   public EventInteract.Action getAction() {
      return this.action;
   }

   public EntityHitResult getHitResult() {
      return this.hitResult;
   }

   public boolean isUsingSecondaryAction() {
      return this.usingSecondaryAction;
   }

   public void setUsingSecondaryAction(boolean usingSecondaryAction) {
      this.usingSecondaryAction = usingSecondaryAction;
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.PRE;
   }

   public enum Action {
      INTERACT,
      ATTACK,
      INTERACT_AT;
   }
}
