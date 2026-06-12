package org.rusherhack.client.api.plugin;

import org.rusherhack.core.logging.ILogger;

public interface IPlugin {
   void onLoad();

   void onUnload();

   ILogger getLogger();
}
