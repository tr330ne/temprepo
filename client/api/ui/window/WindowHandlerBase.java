package org.rusherhack.client.api.ui.window;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.List;
import java.util.ListIterator;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.ui.ElementHandlerBase;
import org.rusherhack.client.api.ui.window.view.WindowView;

public abstract class WindowHandlerBase extends ElementHandlerBase<Window> {
   public WindowHandlerBase() {
      super(false);
   }

   public abstract WindowViewHandlerBase getViewHandler();

   public abstract WindowContentHandlerBase getContentHandler();

   public abstract void renderWindowFrame(Window var1, double var2, double var4);

   public abstract int getFramePadding(WindowHandlerBase.WindowSide var1);

   public void moveElementToTop(Window element) {
      RusherHackAPI.getWindowManager().moveToTop(element);
   }

   @Override
   public List<Window> getElements() {
      return ImmutableList.copyOf(RusherHackAPI.getWindowManager().getFeatures());
   }

   @Override
   public void renderElements(RenderContext context, double mouseX, double mouseY) {
      for (Window window : RusherHackAPI.getWindowManager().getVisibleWindows()) {
         if (!window.isHidden()) {
            this.renderElement(window, context, mouseX, mouseY);
         }
      }
   }

   protected void renderElement(Window window, RenderContext context, double mouseX, double mouseY) {
      IRenderer2D renderer = this.getRenderer();
      PoseStack matrixStack = context.pose();
      boolean building = renderer.isBuilding();
      if (!building) {
         renderer.begin(matrixStack, this.getFontRenderer());
      }

      WindowView view = window.getRootView();
      this.renderWindowFrame(window, mouseX, mouseY);
      int leftPadding = this.getFramePadding(WindowHandlerBase.WindowSide.LEFT);
      int topPadding = this.getFramePadding(WindowHandlerBase.WindowSide.TOP);
      double viewX = window.getX() + leftPadding;
      double viewY = window.getY() + topPadding;
      double viewWidth = window.getWidth() - leftPadding - this.getFramePadding(WindowHandlerBase.WindowSide.RIGHT);
      double viewHeight = window.getHeight() - topPadding - this.getFramePadding(WindowHandlerBase.WindowSide.BOTTOM);
      view.setX(viewX);
      view.setY(viewY);
      view.setViewWidth(viewWidth);
      view.setViewHeight(viewHeight);
      renderer.beginScissor();
      renderer.scissorBox(viewX, viewY, viewWidth, viewHeight);
      this.getContentHandler().handleRenderContent(view, mouseX, mouseY, null);
      renderer.endScissor();
      if (!building) {
         renderer.end();
      }
   }

   @Override
   public void tick() {
      for (Window element : RusherHackAPI.getWindowManager().getVisibleWindows()) {
         if (this.isEnabled(element)) {
            element.tick();
         }
      }
   }

   @Override
   protected boolean consumeMouseClick(double mouseX, double mouseY, int button) {
      List<Window> elements = RusherHackAPI.getWindowManager().getVisibleWindows();
      ListIterator<Window> iterator = elements.listIterator(elements.size());
      boolean consumed = false;

      while (iterator.hasPrevious()) {
         Window element = iterator.previous();
         if (!element.isHidden()) {
            if (!consumed && this.consumeElementMouseClick(element, mouseX, mouseY, button)) {
               consumed = true;
            } else {
               element.setFocused(false);
            }
         }
      }

      return false;
   }

   protected boolean consumeElementMouseClick(Window window, double mouseX, double mouseY, int button) {
      if (window.isHovered(mouseX, mouseY)) {
         this.moveElementToTop(window);
         window.setFocused(true);
         window.mouseClicked(mouseX, mouseY, button);
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void consumeMouseRelease(double mouseX, double mouseY, int button) {
      for (Window element : RusherHackAPI.getWindowManager().getVisibleWindows()) {
         if (this.consumeElementMouseRelease(element, mouseX, mouseY, button)) {
            break;
         }
      }
   }

   protected boolean consumeElementMouseRelease(Window window, double mouseX, double mouseY, int button) {
      window.mouseReleased(mouseX, mouseY, button);
      window.setDragging(false, 0.0, 0.0);
      return false;
   }

   @Override
   protected void consumeMouseMove(double mouseX, double mouseY) {
      Window window = RusherHackAPI.getWindowManager().getFocusedWindow();
      if (window != null) {
         window.mouseMoved(mouseX, mouseY);
      }
   }

   @Override
   public boolean charTyped(char character) {
      Window window = RusherHackAPI.getWindowManager().getFocusedWindow();
      return window != null ? window.charTyped(character) : false;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      Window window = RusherHackAPI.getWindowManager().getFocusedWindow();
      return window != null ? window.keyTyped(key, scanCode, modifiers) : false;
   }

   public boolean isElementHovered(Window window, double mouseX, double mouseY) {
      double x = window.getX();
      double y = window.getY();
      double width = window.getWidth();
      double height = window.getHeight();
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public Color getWindowColor(Window window) {
      Color baseColor = RusherHackAPI.colors().primaryColor().getValue();
      return window.isFocused() ? baseColor : baseColor.darker();
   }

   @Override
   public void initialize() {
   }

   @Override
   public void setDefaultPositions() {
   }

   public enum WindowSide {
      LEFT,
      TOP,
      RIGHT,
      BOTTOM;
   }
}
