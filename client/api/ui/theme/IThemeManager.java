package org.rusherhack.client.api.ui.theme;

import net.minecraft.client.gui.screens.Screen;
import org.rusherhack.client.api.ui.hud.HudHandlerBase;
import org.rusherhack.client.api.ui.panel.PanelHandlerBase;
import org.rusherhack.client.api.ui.window.WindowHandlerBase;

public interface IThemeManager {
   void registerTheme(ITheme var1);

   ITheme getDefaultTheme();

   ITheme getClickGuiTheme();

   ITheme getHudTheme();

   ITheme getWindowsTheme();

   default PanelHandlerBase<?> getClickGuiHandler() {
      return this.getClickGuiTheme().getClickGuiHandler();
   }

   default HudHandlerBase getHudHandler() {
      return this.getHudTheme().getHudHandler();
   }

   default WindowHandlerBase getWindowHandler() {
      return this.getWindowsTheme().getWindowHandler();
   }

   Screen getClickGuiScreen();

   Screen getHudEditorScreen();

   Screen getWindowsScreen();

   @Deprecated
   default ITheme getCurrentTheme() {
      return this.getClickGuiTheme();
   }
}
