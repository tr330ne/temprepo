package org.rusherhack.client.api.events.client.screen;

import java.util.List;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;
import org.rusherhack.core.event.type.EventCancellable;

public abstract class EventScreen extends Event {
   private final Screen screen;

   public EventScreen(Screen screen) {
      this.screen = screen;
   }

   public Screen getScreen() {
      return this.screen;
   }

   public static class BuildButtons extends EventScreen {
      private final List<Renderable> renderables;

      public BuildButtons(Screen screen, List<Renderable> renderables) {
         super(screen);
         this.renderables = renderables;
      }

      public List<Renderable> getElements() {
         return this.renderables;
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

   public static class Change extends EventCancellable {
      private final Screen from;
      private Screen to;

      public Change(Screen from, Screen to) {
         this.from = from;
         this.to = to;
      }

      public Screen getFrom() {
         return this.from;
      }

      public Screen getTo() {
         return this.to;
      }

      public void setScreen(Screen to) {
         this.to = to;
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

   public static class Initialize extends EventScreen {
      public Initialize(Screen screen) {
         super(screen);
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
}
