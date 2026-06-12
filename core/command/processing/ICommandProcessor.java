package org.rusherhack.core.command.processing;

import java.util.function.Predicate;
import org.rusherhack.core.command.argument.parser.IArg;

public interface ICommandProcessor {
   void addArgumentParser(Class<?> var1, IArg<?> var2);

   void addArgumentParser(Predicate<Class<?>> var1, IArg<?> var2);

   IArg<?> getArgumentParser(Class<?> var1);
}
