package org.rusherhack.core.bind;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import org.rusherhack.core.bind.key.IKey;
import org.rusherhack.core.bind.key.NullKey;

public abstract class BindManagerBase {
   private final Object2ObjectMap<IBindable, IKey> BIND_REGISTRY = new Object2ObjectArrayMap();

   public void register(IBindable bindable, IKey key) {
      this.BIND_REGISTRY.put(bindable, key);
   }

   public void unregister(IBindable bindable) {
      this.BIND_REGISTRY.remove(bindable);
   }

   public void setBind(IBindable bindable, IKey key) {
      if (key == null) {
         key = NullKey.INSTANCE;
      }

      this.BIND_REGISTRY.replace(bindable, key);
   }

   public void setBind(String bindableName, IKey key) {
      this.BIND_REGISTRY
         .keySet()
         .stream()
         .filter(bindable -> bindable.getBindReference().equalsIgnoreCase(bindableName))
         .findFirst()
         .ifPresent(bindable -> this.BIND_REGISTRY.replace(bindable, key));
   }

   public IKey getBind(IBindable bindable) {
      return (IKey)this.BIND_REGISTRY.getOrDefault(bindable, NullKey.INSTANCE);
   }

   public Object2ObjectMap<IBindable, IKey> getBindRegistry() {
      return Object2ObjectMaps.unmodifiable(this.BIND_REGISTRY);
   }
}
