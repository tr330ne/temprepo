package org.rusherhack.client.api.ui.window.view;

import java.util.List;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.ui.window.WindowHandlerBase;
import org.rusherhack.client.api.ui.window.WindowViewHandlerBase;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.core.interfaces.INamed;

public abstract class WindowView extends WindowContent implements INamed {
   protected double viewWidth;
   protected double viewHeight;
   protected List<? extends WindowContent> contentList;
   protected String viewName;

   public WindowView(Window window, List<? extends WindowContent> contentList) {
      this("unnamed", window, contentList);
   }

   public WindowView(String name, Window window, List<? extends WindowContent> contentList) {
      super(window);
      this.viewName = name;
      this.contentList = contentList;
   }

   public abstract void renderViewContent(double var1, double var3);

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
      synchronized (this.getContent()) {
         IRenderer2D renderer = this.getRenderer();
         renderer.scissorBox(this.getX(), this.getY(), this.getWidth(), this.getHeight());
         this.getViewHandler().handleRenderViewContent(this, mouseX, mouseY);
         renderer.popScissorBox();
      }
   }

   @Override
   public void unfocus() {
      for (WindowContent content : this.contentList) {
         content.unfocus();
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return this.getViewHandler().handleViewMouseClicked(this, mouseX, mouseY, button);
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      for (WindowContent content : this.contentList) {
         content.mouseReleased(mouseX, mouseY, button);
      }
   }

   @Override
   public boolean charTyped(char character) {
      boolean consumed = false;

      for (WindowContent content : this.contentList) {
         if (content.charTyped(character)) {
            consumed = true;
         }
      }

      return consumed;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      boolean consumed = false;

      for (WindowContent content : this.contentList) {
         if (content.keyTyped(key, scanCode, modifiers)) {
            consumed = true;
         }
      }

      return consumed;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      boolean consumed = false;

      for (WindowContent content : this.contentList) {
         if (content.mouseScrolled(mouseX, mouseY, delta)) {
            consumed = true;
         }
      }

      return consumed;
   }

   @Override
   public void tick() {
      for (WindowContent content : this.contentList) {
         content.tick();
      }
   }

   public void setViewWidth(double viewWidth) {
      this.viewWidth = viewWidth;
   }

   public void setViewHeight(double viewHeight) {
      this.viewHeight = viewHeight;
   }

   @Override
   public double getWidth() {
      return this.viewWidth;
   }

   @Override
   public double getHeight() {
      return this.viewHeight;
   }

   public double getViewWidth() {
      return this.getWidth();
   }

   public double getViewHeight() {
      return this.getHeight();
   }

   @Override
   public String getName() {
      return this.viewName;
   }

   public void add(WindowContent content) {
      this.getContent().add(content);
   }

   public void remove(WindowContent content) {
      this.getContent().remove(content);
   }

   public List<WindowContent> getContent() {
      return this.contentList;
   }

   public void setContentList(List<? extends WindowContent> contentList) {
      this.contentList = contentList;
   }

   protected WindowHandlerBase getHandler() {
      return RusherHackAPI.getWindowManager().getWindowHandler();
   }

   protected WindowViewHandlerBase getViewHandler() {
      return RusherHackAPI.getWindowManager().getWindowHandler().getViewHandler();
   }
}
