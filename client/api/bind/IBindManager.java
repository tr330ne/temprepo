package org.rusherhack.client.api.bind;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import org.rusherhack.core.bind.IBindable;
import org.rusherhack.core.bind.key.IKey;
import org.rusherhack.core.bind.key.NullKey;

public interface IBindManager {
   default void register(IBindable bindable) {
      this.register(bindable, NullKey.INSTANCE);
   }

   void register(IBindable var1, IKey var2);

   void unregister(IBindable var1);

   void setBind(IBindable var1, IKey var2);

   void setBind(String var1, IKey var2);

   IKey getBind(IBindable var1);

   Object2ObjectMap<IBindable, IKey> getBindRegistry();

   IKey parseKey(String var1);

   IKey createKeyboardKey(int var1);

   IKey createMouseKey(int var1);
}
