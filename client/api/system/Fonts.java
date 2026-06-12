package org.rusherhack.client.api.system;

import org.rusherhack.client.api.render.font.IFontRenderer;

public interface Fonts {
   IFontRenderer getFontRenderer();

   IFontRenderer getCustomFontRenderer();

   IFontRenderer getVanillaFontRenderer();

   IFontRenderer getClickGuiFontRenderer();

   IFontRenderer getHudFontRenderer();

   IFontRenderer getWindowFontRenderer();
}
