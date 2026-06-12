package org.rusherhack.client.api.plugin;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.core.logging.ILogger;

public abstract class Plugin implements IPlugin {
   protected final ILogger logger = RusherHackAPI.createLogger(this.getClass().getSimpleName());

   @Override
   public ILogger getLogger() {
      return this.logger;
   }
}
