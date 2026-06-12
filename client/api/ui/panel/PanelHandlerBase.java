package org.rusherhack.client.api.ui.panel;

import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.ui.ElementBase;
import org.rusherhack.client.api.ui.ElementHandlerBase;

public abstract class PanelHandlerBase<T extends ElementBase> extends ElementHandlerBase<T> {
   protected final List<T> panels = new ArrayList<>();

   public PanelHandlerBase(boolean scaledWithMinecraftGui) {
      super(scaledWithMinecraftGui);
   }

   public abstract T createPanel(String var1);

   public void addPanel(T panel) {
      this.panels.add(panel);
   }

   @Override
   public void moveElementToTop(T element) {
      this.panels.remove(element);
      this.panels.add(element);
   }

   @Override
   public List<T> getElements() {
      return this.panels;
   }

   @Override
   public IFontRenderer getFontRenderer() {
      return RusherHackAPI.fonts().getClickGuiFontRenderer();
   }
}
