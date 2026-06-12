package org.rusherhack.core.feature;

import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.interfaces.IReferenceable;

public interface IFeature extends INamed, IReferenceable {
   String getDescription();

   default boolean reset() {
      return false;
   }

   @Override
   default String getReferenceKey() {
      return this.getName().replace(" ", "_").toLowerCase();
   }
}
