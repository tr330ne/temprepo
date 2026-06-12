package org.rusherhack.client.api.feature.module;

import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.interfaces.IHideable;
import org.rusherhack.core.notification.INotifiable;
import org.rusherhack.core.serialize.JsonSerializable;

public interface IModule extends IFeatureConfigurable, INotifiable, IHideable, JsonSerializable {
   ModuleCategory getCategory();

   default String getMetadata() {
      return "";
   }

   boolean isDrawn();

   void setDrawn(boolean var1);
}
