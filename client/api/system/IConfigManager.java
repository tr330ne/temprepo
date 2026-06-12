package org.rusherhack.client.api.system;

import org.rusherhack.client.api.config.Configuration;
import org.rusherhack.core.serialize.ISerializable;

public interface IConfigManager {
   void registerConfig(Configuration var1, ISerializable<?> var2);

   boolean reloadConfig(Configuration var1);

   boolean saveConfig(Configuration var1);

   void load();

   void save();
}
