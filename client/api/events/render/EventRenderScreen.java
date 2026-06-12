package org.rusherhack.client.api.events.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public class EventRenderScreen extends EventCancellable {
   private final Screen screen;
   private final GuiGraphics graphics;
   private int mouseX;
   private int mouseY;
   private final float partialTick;

   public EventRenderScreen(Screen screen, GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.screen = screen;
      this.graphics = graphics;
      this.mouseX = mouseX;
      this.mouseY = mouseY;
      this.partialTick = partialTick;
   }

   public Screen getScreen() {
      return this.screen;
   }

   public GuiGraphics getGraphics() {
      return this.graphics;
   }

   public PoseStack getMatrixStack() {
      return this.graphics.pose();
   }

   public int getMouseX() {
      return this.mouseX;
   }

   public void setMouseX(int mouseX) {
      this.mouseX = mouseX;
   }

   public int getMouseY() {
      return this.mouseY;
   }

   public void setMouseY(int mouseY) {
      this.mouseY = mouseY;
   }

   public float getPartialTick() {
      return this.partialTick;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.POST;
   }

   public static class Background extends EventCancellable {
      private final Screen screen;
      private final GuiGraphics graphics;

      public Background(Screen screen, GuiGraphics graphics) {
         this.screen = screen;
         this.graphics = graphics;
      }

      public Screen getScreen() {
         return this.screen;
      }

      public GuiGraphics getGraphics() {
         return this.graphics;
      }
   }
}
