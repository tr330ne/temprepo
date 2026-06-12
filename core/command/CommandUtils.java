package org.rusherhack.core.command;

import java.util.ArrayList;
import java.util.List;

public class CommandUtils {
   public static List<String> processArguments(String input, boolean removeQuotes) {
      input = input.trim();
      List<String> arguments = new ArrayList<>();
      StringBuilder builder = new StringBuilder();
      boolean inQuotes = false;

      for (int i = 0; i < input.length(); i++) {
         char c = input.charAt(i);
         if (c == '"' || c == 8220 || c == 8221) {
            inQuotes = !inQuotes;
            if (!removeQuotes) {
               builder.append(c);
            }
         } else if (c == ' ' && !inQuotes) {
            arguments.add(builder.toString());
            builder.setLength(0);
         } else {
            builder.append(c);
         }
      }

      if (!builder.isEmpty()) {
         arguments.add(builder.toString());
      }

      return arguments;
   }

   public static List<String> generateSyntaxList(CommandData commandData, boolean subCommands, boolean includeName) {
      return generateSyntaxList(commandData, subCommands, includeName, 99);
   }

   public static List<String> generateSyntaxList(CommandData commandData, boolean subCommands, boolean includeName, int maxDepth) {
      return generateSyntaxList(commandData, subCommands, includeName, maxDepth, 0);
   }

   private static List<String> generateSyntaxList(CommandData commandData, boolean subCommands, boolean includeName, int maxDepth, int depth) {
      List<String> syntaxList = new ArrayList<>();

      for (CommandData.MethodData method : commandData.getMethods()) {
         StringBuilder builder = new StringBuilder();
         if (includeName) {
            for (CommandData temp = commandData; temp.getParent() != null; temp = temp.getParent()) {
               builder.insert(0, temp.getParent().getName() + " ");
            }

            builder.append(commandData.getName());
         }

         method.arguments().forEach(argumentData -> builder.append(String.format(argumentData.optional() ? " [%s]" : " <%s>", argumentData.name())));
         syntaxList.add(builder.toString());
      }

      if (subCommands && depth < maxDepth) {
         for (CommandData subCommand : commandData.getSubCommands()) {
            syntaxList.addAll(generateSyntaxList(subCommand, true, true, maxDepth, depth + 1));
         }
      }

      return syntaxList;
   }
}
