package org.rusherhack.core.event.type;

import org.rusherhack.core.event.stage.IStageable;
import org.rusherhack.core.event.stage.Stage;

public abstract class Event implements IStageable {
   private Stage stage;

   public Event() {
      this.stage = Stage.ON;
   }

   public Event(Stage stage) {
      this.stage = stage;
   }

   public void setStage(Stage stage) {
      this.stage = stage;
   }

   @Override
   public Stage getStage() {
      return this.stage;
   }

   public Stage getPreferredStage() {
      return Stage.ON;
   }
}
