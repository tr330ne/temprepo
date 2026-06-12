package org.rusherhack.client.api.events.internal;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;

public class EventCommand extends Event {
   private final String input;
   private Component output = null;

   public EventCommand(String input) {
      this.input = input;
   }

   public String getInput() {
      return this.input;
   }

   @Deprecated
   @Nullable
   public String getOutput() {
      return this.output == null ? null : this.output.getString();
   }

   @Nullable
   public Component getOutputAsComponent() {
      return this.output;
   }

   public void setOutput(Component output) {
      this.output = output;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }
}
