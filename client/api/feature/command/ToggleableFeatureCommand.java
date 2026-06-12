package org.rusherhack.client.api.feature.command;

import org.rusherhack.core.command.annotations.CommandExecutor;
import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.interfaces.IToggleable;

public class ToggleableFeatureCommand extends FeatureCommand {
   public ToggleableFeatureCommand(IFeatureConfigurable feature) {
      super(feature);
   }

   @CommandExecutor
   private String toggleFeature() {
      IFeatureConfigurable feature = this.getFeature();
      if (feature instanceof IToggleable toggleable) {
         toggleable.toggle();
         return "Toggled " + feature.getName() + " to " + toggleable.isToggled();
      } else {
         return "Feature " + feature.getName() + " is not toggleable";
      }
   }
}
