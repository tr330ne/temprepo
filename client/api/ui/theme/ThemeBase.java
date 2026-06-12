package org.rusherhack.client.api.ui.theme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.core.setting.Setting;

public abstract class ThemeBase implements ITheme {
   private final String name;
   private final String description;
   private final List<Setting<?>> settings = new ArrayList<>();
   private final ColorSetting color = new ColorSetting("Color", new Color(0, 100, 255, 200)).setThemeSync(false).setThemeSyncAllowed(false);

   public ThemeBase(String name, String description, Color defaultColor) {
      this.name = name;
      this.description = description;
      this.color.setValue(defaultColor);
      this.registerSettings(this.color);
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getDescription() {
      return this.description;
   }

   @Override
   public List<Setting<?>> getSettings() {
      return this.settings;
   }

   @Override
   public ColorSetting getColorSetting() {
      return this.color;
   }

   @Override
   public Color getPrimaryColor() {
      return this.color.getValue();
   }
}
