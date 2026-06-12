package org.rusherhack.client.api.system;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.window.PinnableWindow;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.ui.window.WindowHandlerBase;
import org.rusherhack.core.feature.IFeatureManager;

public interface IWindowManager extends IFeatureManager<Window> {
   void moveToTop(Window var1);

   void popupWindow(Window var1);

   void closePopup(Window var1);

   IRenderer2D getRenderer();

   IFontRenderer getFontRenderer();

   WindowHandlerBase getWindowHandler();

   @Nullable
   default Window getFocusedWindow() {
      List<Window> windows = this.getVisibleWindows();
      if (windows.isEmpty()) {
         return null;
      }

      Window topWindow = windows.get(windows.size() - 1);
      return topWindow.isFocused() ? topWindow : null;
   }

   default List<Window> getVisibleWindows() {
      boolean onlyPinned = Globals.mc.screen != RusherHackAPI.getThemeManager().getWindowsScreen();
      List<Window> visibleWindows = new ArrayList<>();

      for (Window window : this.getFeatures()) {
         if ((!onlyPinned || window instanceof PinnableWindow pinnable && pinnable.isPinned()) && !window.isHidden()) {
            visibleWindows.add(window);
         }
      }

      return visibleWindows;
   }
}
