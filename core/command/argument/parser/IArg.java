package org.rusherhack.core.command.argument.parser;

import java.util.ArrayList;
import java.util.List;
import org.rusherhack.core.command.CommandData;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.processing.CommandProcessingSink;

public interface IArg<T> {
   List<String> EMPTY_SUGGESTIONS = new ArrayList<>();

   T parse(CommandProcessingSink var1, String var2) throws ArgumentException;

   default List<String> getSuggestions(CommandProcessingSink context, CommandData.ArgumentData argument, String typedArgument) {
      return EMPTY_SUGGESTIONS;
   }

   default boolean captureRemaining() {
      return false;
   }
}
