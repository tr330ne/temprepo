package org.rusherhack.client.api.system;

public interface IFieldRegistry {
   <T> T accessField(Object var1, String var2);

   void registerField(Object var1, String var2, IFieldRegistry.Accessor var3);

   interface Accessor {
      Object getValue();
   }
}
