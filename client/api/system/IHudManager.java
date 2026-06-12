package org.rusherhack.client.api.system;

import java.awt.Color;
import org.rusherhack.client.api.feature.hud.HudElement;
import org.rusherhack.core.feature.IFeatureManager;

public interface IHudManager extends IFeatureManager<HudElement> {
   void moveToTop(HudElement var1);

   double getScale();

   Color getPrimaryColor();

   Color getSecondaryColor();

   Color getBackgroundColor();

   boolean isThemeColorSynced();

   boolean isGlobalColorSynced();

   Color getModuleToggleColor(boolean var1);
}
