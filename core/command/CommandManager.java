package org.rusherhack.core.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.rusherhack.core.command.annotations.CommandExecutor;
import org.rusherhack.core.command.dispatch.ICommandDispatcher;
import org.rusherhack.core.command.dispatch.StringCommandDispatcher;
import org.rusherhack.core.command.processing.CommandProcessor;
import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.logging.ILogger;

public class CommandManager implements ICommandManager {
   private final Map<AbstractCommand, CommandData> commands = new HashMap<>();
   protected final CommandProcessor commandProcessor;
   protected ICommandDispatcher<?> dispatcher;
   protected final ILogger logger;

   public CommandManager(ILogger logger) {
      this(new CommandProcessor(), null, logger);
   }

   public CommandManager(CommandProcessor processor, ILogger logger) {
      this(processor, null, logger);
   }

   public CommandManager(CommandProcessor processor, ICommandDispatcher<?> dispatcher, ILogger logger) {
      this.logger = logger;
      if (dispatcher == null) {
         dispatcher = new StringCommandDispatcher(this);
      }

      this.commandProcessor = processor;
      this.dispatcher = dispatcher;
   }

   public void registerFeature(AbstractCommand commandObject) {
      CommandData commandData = this.createCommandData(commandObject);
      this.commands.put(commandObject, commandData);
   }

   public CommandData createCommandData(AbstractCommand command) {
      CommandData commandData = new CommandData(command);
      List<Method> methodsToScan = new ArrayList<>();

      for (Class<?> clazz = command.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
         methodsToScan.addAll(Arrays.asList(clazz.getDeclaredMethods()));
      }

      for (Method method : methodsToScan) {
         try {
            if (method.isAnnotationPresent(CommandExecutor.class)) {
               if (!method.isAccessible()) {
                  method.setAccessible(true);
               }

               String[] subCommandAliases = method.getAnnotation(CommandExecutor.class).subCommand();
               boolean isSubCommand = subCommandAliases.length > 0;
               List<CommandData.ArgumentData> arguments = new ArrayList<>();
               boolean argumentsPresent = method.isAnnotationPresent(CommandExecutor.Argument.class);
               if (argumentsPresent) {
                  CommandExecutor.Argument args = method.getAnnotation(CommandExecutor.Argument.class);

                  for (int i = 0; i < args.value().length; i++) {
                     String argumentName = args.value()[i];
                     Class<?> argumentType = method.getParameterTypes()[i];
                     boolean optional = false;
                     if (argumentType.isAssignableFrom(Optional.class)) {
                        Class<?> optionalType = Class.forName(method.getGenericParameterTypes()[i].getTypeName().split("<")[1].split(">")[0]);
                        argumentType = optionalType;
                        optional = true;
                     }

                     arguments.add(new CommandData.ArgumentData(i, argumentName, argumentType, optional));
                  }
               }

               if (!isSubCommand) {
                  commandData.addMethod(method, arguments);
               } else {
                  CommandData parentCommand = commandData;

                  for (int i = 0; i < subCommandAliases[0].split(" ").length; i++) {
                     String subCommand = subCommandAliases[0].split(" ")[i];
                     CommandData subCommandData = parentCommand.findSubCommand(subCommand);
                     if (subCommandData != null) {
                        parentCommand = subCommandData;
                     } else {
                        String[] aliases = new String[subCommandAliases.length];

                        for (int j = 0; j < subCommandAliases.length; j++) {
                           aliases[j] = subCommandAliases[j].split(" ")[i];
                        }

                        CommandData newSubCommand = new CommandData(command, subCommand, aliases);
                        newSubCommand.setParent(parentCommand);
                        parentCommand.getSubCommands().add(newSubCommand);
                        parentCommand = newSubCommand;
                     }
                  }

                  parentCommand.addMethod(method, arguments);
               }
            }
         } catch (Throwable t) {
            this.logger.error("Failed to register command " + command.getName(), t);
         }
      }

      for (AbstractCommand subCommand : command.getSubCommands()) {
         CommandData subCommandData = this.createCommandData(subCommand);
         subCommandData.setParent(commandData);
         commandData.getSubCommands().add(subCommandData);
      }

      return commandData;
   }

   public CommandData getCommandData(String command) {
      for (CommandData commandData : this.commands.values()) {
         for (String alias : commandData.getCommand().getAliases()) {
            if (alias.equalsIgnoreCase(command)) {
               return commandData;
            }
         }
      }

      return null;
   }

   public CommandData getCommandData(AbstractCommand command) {
      return this.commands.get(command);
   }

   @Override
   public Optional<AbstractCommand> getFeature(String name) {
      for (AbstractCommand command : this.commands.keySet()) {
         for (String alias : command.getAliases()) {
            if (alias.equalsIgnoreCase(name)) {
               return Optional.of(command);
            }
         }
      }

      return Optional.empty();
   }

   public List<AbstractCommand> getFeatures() {
      List<AbstractCommand> list = new ArrayList<>(this.commands.keySet());
      list.sort(Comparator.comparing(INamed::getName));
      return list;
   }

   public CommandProcessor getCommandProcessor() {
      return this.commandProcessor;
   }

   @Override
   public ICommandDispatcher<?> getDispatcher() {
      return this.dispatcher;
   }

   @Override
   public String getPrefix() {
      return "";
   }

   public ILogger getLogger() {
      return this.logger;
   }
}
