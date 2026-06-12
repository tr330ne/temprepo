package org.rusherhack.core.feature;

import java.util.List;
import org.rusherhack.core.setting.Setting;

public interface IFeatureConfigurable extends IFeature {
   List<Setting<?>> getSettings();

   default void registerSettings(Setting<?>... settings) {
      for (Setting<?> setting : settings) {
         this.getSettings().add(setting);
         setting.setAttachedFeature(this);
      }
   }

   default Setting<?> getSetting(String name) {
      String[] settings = name.replace(":", ".").split("\\.");
      String baseSettingName = settings[0];

      for (Setting<?> setting : this.getSettings()) {
         for (String alias : setting.getAliases()) {
            if (alias.equalsIgnoreCase(baseSettingName)) {
               if (settings.length > 1) {
                  return setting.getSubSetting(name.substring(baseSettingName.length() + 1));
               }

               return setting;
            }
         }
      }

      return null;
   }

   @Override
   default boolean reset() {
      for (Setting<?> setting : this.getSettings()) {
         setting.reset();
      }

      return true;
   }
}
