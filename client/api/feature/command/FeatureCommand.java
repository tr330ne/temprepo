package org.rusherhack.client.api.feature.command;

import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.setting.Setting;

public class FeatureCommand extends Command {
   private final IFeatureConfigurable feature;

   public FeatureCommand(IFeatureConfigurable feature) {
      super(feature.getName().toLowerCase(), "Configure the " + feature.getName() + " feature");
      this.feature = feature;

      for (Setting<?> setting : this.feature.getSettings()) {
         AbstractCommand subCommand = setting.createCommand(this);
         if (subCommand != null) {
            this.registerSubCommand(subCommand);
         }

         for (Setting<?> subSetting : setting.getAllSubSettings()) {
            AbstractCommand sc = subSetting.createCommand(this);
            if (sc != null) {
               this.registerSubCommand(sc);
            }
         }
      }
   }

   @Override
   public boolean isHidden() {
      return true;
   }

   public IFeatureConfigurable getFeature() {
      return this.feature;
   }
}
