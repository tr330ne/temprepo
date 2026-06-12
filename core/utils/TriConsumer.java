package org.rusherhack.core.utils;

@FunctionalInterface
public interface TriConsumer<K, V, S> {
   void accept(K var1, V var2, S var3);
}
