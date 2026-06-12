package org.rusherhack.client.api.events.world;

import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;

public class EventLoadWorld extends Event {
   @Override
   public Stage getPreferredStage() {
      return Stage.POST;
   }
}
