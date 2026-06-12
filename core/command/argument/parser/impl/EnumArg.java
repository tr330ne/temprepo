package org.rusherhack.core.command.argument.parser.impl;

import java.util.ArrayList;
import java.util.List;
import org.rusherhack.core.command.CommandData;
import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.processing.CommandProcessingSink;
import org.rusherhack.core.utils.StringUtils;

public class EnumArg<T extends Enum<T>> implements IArg<T> {
   public T parse(CommandProcessingSink context, String arg) throws ArgumentException {
      CommandData.MethodData methodData = context.getCurrentEntry().getMethodData();
      CommandData.ArgumentData argument = methodData.arguments().get(context.getCurrentEntry().getParsedArguments().size());
      Class<?> type = argument.type();
      if (!type.isEnum()) {
         throw new ArgumentException("Invalid enum type: " + type.getSimpleName());
      }

      Class<T> enumType = (Class<T>)type;

      for (Enum<T> enumConstant : (Enum[])enumType.getEnumConstants()) {
         String name = enumConstant.name();
         if (name.equalsIgnoreCase(arg) || StringUtils.toTitleCase(name).equalsIgnoreCase(arg)) {
            return (T)enumConstant;
         }
      }

      throw new ArgumentException("Could not find enum constant: " + arg);
   }

   @Override
   public List<String> getSuggestions(CommandProcessingSink context, CommandData.ArgumentData argument, String typedArgument) {
      ArrayList<String> suggestions = new ArrayList<>();
      Class<?> type = argument.type();
      if (type.isEnum()) {
         Class<T> enumType = (Class<T>)type;

         for (Enum<T> enumConstant : (Enum[])enumType.getEnumConstants()) {
            suggestions.add(StringUtils.toTitleCase(enumConstant.name()));
         }
      }

      return suggestions;
   }
}
