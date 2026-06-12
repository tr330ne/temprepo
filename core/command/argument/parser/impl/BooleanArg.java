package org.rusherhack.core.command.argument.parser.impl;

import java.util.ArrayList;
import java.util.List;
import org.rusherhack.core.command.CommandData;
import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.processing.CommandProcessingSink;

public class BooleanArg implements IArg<Boolean> {
   public static final String[] TRUE_ALIASES = new String[]{"true", "on", "enable", "enabled", "1", "yes"};
   public static final String[] FALSE_ALIASES = new String[]{"false", "off", "disable", "disabled", "0", "no"};

   public Boolean parse(CommandProcessingSink context, String arg) throws ArgumentException {
      for (String alias : TRUE_ALIASES) {
         if (alias.equalsIgnoreCase(arg)) {
            return true;
         }
      }

      for (String alias : FALSE_ALIASES) {
         if (alias.equalsIgnoreCase(arg)) {
            return false;
         }
      }

      throw new ArgumentException("Invalid boolean: " + arg);
   }

   @Override
   public List<String> getSuggestions(CommandProcessingSink context, CommandData.ArgumentData argument, String typedArgument) {
      List<String> suggestions = new ArrayList<>();

      for (String trueAliases : TRUE_ALIASES) {
         if (trueAliases.toLowerCase().startsWith(typedArgument.toLowerCase())) {
            suggestions.add(trueAliases);
            break;
         }
      }

      for (String falseAliases : FALSE_ALIASES) {
         if (falseAliases.toLowerCase().startsWith(typedArgument.toLowerCase())) {
            suggestions.add(falseAliases);
            break;
         }
      }

      return suggestions;
   }
}
