package org.rusherhack.client.api.ui.window;

import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.view.WindowView;

public abstract class WindowViewHandlerBase {
   protected final WindowHandlerBase windowHandler;

   public WindowViewHandlerBase(WindowHandlerBase windowHandler) {
      this.windowHandler = windowHandler;
   }

   public void handleRenderViewContent(WindowView view, double mouseX, double mouseY) {
      view.renderViewContent(mouseX, mouseY);
   }

   public boolean handleViewMouseClicked(WindowView view, double mouseX, double mouseY, int button) {
      boolean consumed = false;

      for (WindowContent content : view.getContent()) {
         if (this.windowHandler.getContentHandler().handleMouseClicked(content, mouseX, mouseY, button, view)) {
            consumed = true;
         }
      }

      return consumed;
   }
}
