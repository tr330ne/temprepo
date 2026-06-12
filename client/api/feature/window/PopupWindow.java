package org.rusherhack.client.api.feature.window;

import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.view.SimpleView;
import org.rusherhack.client.api.ui.window.view.WindowView;

public abstract class PopupWindow extends Window {
   protected final List<WindowContent> content = new ArrayList<>();
   protected WindowView rootView;

   public PopupWindow(String title, Window parent, double width, double height) {
      super(
         title,
         (
               parent != null
                  ? parent.getX() + parent.getWidth() / 2.0
                  : Globals.mc.getWindow().getGuiScaledWidth() / RusherHackAPI.getThemeManager().getWindowHandler().getScale() / 2.0F
            )
            - width / 2.0,
         (
               parent != null
                  ? parent.getY() + parent.getHeight() / 2.0
                  : Globals.mc.getWindow().getGuiScaledHeight() / RusherHackAPI.getThemeManager().getWindowHandler().getScale() / 2.0F
            )
            - height / 2.0,
         width,
         height
      );
      SimpleView simpleView = new SimpleView(this, this.content);
      simpleView.setAlignment(SimpleView.Alignment.CENTER);
      this.rootView = simpleView;
   }

   public void addContent(WindowContent content) {
      this.content.add(content);
   }

   @Override
   public WindowView getRootView() {
      return this.rootView;
   }

   @Override
   public void onClose() {
      RusherHackAPI.getWindowManager().closePopup(this);
   }

   @Override
   public boolean shouldSerialize(boolean autosave) {
      return false;
   }
}
