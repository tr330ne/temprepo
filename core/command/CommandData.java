package org.rusherhack.core.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.rusherhack.core.interfaces.INamed;

public class CommandData implements INamed {
   private final AbstractCommand command;
   private final String name;
   private final String[] aliases;
   private final List<CommandData.MethodData> methods = new ArrayList<>();
   private CommandData parent = null;
   private final List<CommandData> subCommands = new ArrayList<>();

   public CommandData(AbstractCommand command) {
      this(command, command.getName().toLowerCase(), command.getAliases());
   }

   public CommandData(AbstractCommand command, String name, String... aliases) {
      this.command = command;
      this.name = name;
      this.aliases = aliases;
   }

   public AbstractCommand getCommand() {
      return this.command;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String[] getAliases() {
      return this.aliases;
   }

   public void addMethod(Method method, List<CommandData.ArgumentData> arguments) {
      this.methods.add(new CommandData.MethodData(method, arguments));
      this.methods.sort(Comparator.comparingInt(a -> a.arguments.size()));
   }

   public List<CommandData> getSubCommands() {
      return this.subCommands;
   }

   public CommandData findSubCommand(String alias) {
      for (CommandData subCommand : this.subCommands) {
         for (String subCommandAlias : subCommand.getAliases()) {
            if (subCommandAlias.equalsIgnoreCase(alias)) {
               return subCommand;
            }
         }

         if (subCommand.getName().equalsIgnoreCase(alias)) {
            return subCommand;
         }
      }

      return null;
   }

   public List<CommandData.MethodData> getMethods() {
      return this.methods;
   }

   public void setParent(CommandData parent) {
      this.parent = parent;
   }

   public CommandData getParent() {
      return this.parent;
   }

   public record ArgumentData(int index, String name, Class<?> type, boolean optional) {
   }

   public record MethodData(Method method, List<CommandData.ArgumentData> arguments) {
   }
}
