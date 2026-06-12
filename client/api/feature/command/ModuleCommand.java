package org.rusherhack.client.api.feature.command;

import org.rusherhack.client.api.feature.module.IModule;

public class ModuleCommand extends ToggleableFeatureCommand {
   public ModuleCommand(IModule module) {
      super(module);
   }
}
