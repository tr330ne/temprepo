package org.rusherhack.core.command.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.rusherhack.core.command.CommandData;
import org.rusherhack.core.command.CommandManager;
import org.rusherhack.core.command.CommandUtils;
import org.rusherhack.core.command.ICommandSource;
import org.rusherhack.core.command.exceptions.CommandException;
import org.rusherhack.core.command.exceptions.DispatchException;
import org.rusherhack.core.command.exceptions.UnknownCommandException;
import org.rusherhack.core.command.exceptions.ValidationException;
import org.rusherhack.core.command.processing.CommandProcessingSink;

public class StringCommandDispatcher implements ICommandDispatcher<String> {
   protected final CommandManager commandManager;

   public StringCommandDispatcher(CommandManager commandManager) {
      this.commandManager = commandManager;
   }

   public String execute(ICommandSource source, String input) {
      try {
         return this.dispatch(source, input);
      } catch (UnknownCommandException exception) {
         return exception.getMessage() + " (Type " + this.commandManager.getPrefix() + "help for a list of commands)";
      } catch (ValidationException exception) {
         return exception.getMessage();
      } catch (CommandException exception) {
         StringBuilder output = new StringBuilder(exception.getMessage());
         CommandProcessingSink context = exception.getContext();
         if (context == null) {
            return output.toString();
         }

         List<String> args = CommandUtils.processArguments(context.getCommandString(), false);
         CommandProcessingSink.MethodProcessor methodProcessor = context.getCurrentEntry();
         int badSyntaxIndex = methodProcessor == null ? args.size() : methodProcessor.getBaseDepth() + methodProcessor.getParsedArguments().size() + 1;
         output.append("\n\nINPUT: ");

         for (int i = 0; i <= badSyntaxIndex; i++) {
            String arg = "[missing argument]";

            try {
               arg = args.get(i);
            } catch (IndexOutOfBoundsException var12) {
            }

            if (i == badSyntaxIndex) {
               output.append(" >>> ").append(arg).append(" <<< ");
            } else {
               output.append(arg).append(" ");
            }
         }

         if (exception.shouldPrintSyntax()) {
            output.append("\n\n");
            output.append("USAGE:    ");
            List<String> commandSyntaxList = CommandUtils.generateSyntaxList(context.getCommandData(), true, true);

            for (int i = 0; i < commandSyntaxList.size(); i++) {
               String syntax = commandSyntaxList.get(i);
               if (i != 0) {
                  output.append("\n\t\tOR:    ");
               }

               output.append(syntax);
            }
         }

         return output.toString();
      } catch (Throwable t) {
         String errorStr = String.format("An error occurred while executing \"%s\" command: %s. Check console for more information.", input, t.getMessage());
         this.commandManager.getLogger().error("Error whilst executing {0}", input, t);
         return errorStr;
      }
   }

   public String dispatch(ICommandSource source, String input) throws CommandException, DispatchException {
      if (input.isEmpty()) {
         throw new UnknownCommandException("Empty command");
      }

      CommandProcessingSink context = this.commandManager.getCommandProcessor().processCommand(this.commandManager, input);
      if (!source.validate(context.getCommandData())) {
         throw new ValidationException("You do not have permission to execute this command");
      }

      CommandProcessingSink.ProcessedCommand processed = context.finish(false);
      if (processed == null) {
         throw new UnknownCommandException("Unknown command: " + input);
      }

      CommandData commandData = processed.commandData();
      Method method = processed.methodData().method();
      Map<CommandData.ArgumentData, Object> arguments = processed.parsedArguments();

      try {
         if (method.getReturnType().isAssignableFrom(String.class)) {
            return (String)method.invoke(commandData.getCommand(), arguments.values().toArray());
         }

         method.invoke(commandData.getCommand(), arguments.values().toArray());
         return null;
      } catch (InvocationTargetException | IllegalAccessException e) {
         if (e.getCause() instanceof CommandException cmdException) {
            throw cmdException;
         } else {
            throw new DispatchException("Failed to dispatch command", e);
         }
      }
   }
}
