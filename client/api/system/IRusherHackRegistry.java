package org.rusherhack.client.api.system;

import org.rusherhack.core.interfaces.IReferenceable;

public interface IRusherHackRegistry {
   void register(IReferenceable var1);

   IReferenceable get(String var1);
}
