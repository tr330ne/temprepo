package org.rusherhack.core.feature;

import java.util.Optional;

public interface IFeatureManager<T extends IFeature> {
   void registerFeature(T var1);

   Optional<T> getFeature(String var1);

   Iterable<T> getFeatures();
}
