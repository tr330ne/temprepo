package org.rusherhack.client.api.events.client.input;

import org.rusherhack.client.api.utils.objects.PlayerInput;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;

public class EventInputTick extends Event {
   private final PlayerInput.Builder input;

   public EventInputTick(PlayerInput.Builder input) {
      this.input = input;
   }

   public PlayerInput.Builder getInput() {
      return this.input;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.POST;
   }
}
