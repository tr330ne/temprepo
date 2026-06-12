package org.rusherhack.core.command.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Consumer;
import org.rusherhack.core.command.CommandData;
import org.rusherhack.core.command.CommandManager;
import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.exceptions.BadSyntaxException;
import org.rusherhack.core.command.exceptions.CommandException;
import org.rusherhack.core.command.exceptions.UnknownArgumentTypeException;
import org.rusherhack.core.command.exceptions.UnknownCommandException;

public class CommandProcessingSink implements Consumer<String> {
   protected final CommandManager commandManager;
   protected final CommandProcessor commandProcessor;
   protected CommandData commandData = null;
   protected final StringJoiner commandString = new StringJoiner(" ");
   protected StringBuilder inputBuffer = new StringBuilder();
   protected ArrayList<String> arguments = new ArrayList<>();
   protected LinkedHashMap<CommandData, HashSet<CommandProcessingSink.MethodProcessor>> candidates = new LinkedHashMap<>();
   private CommandProcessingSink.MethodProcessor currentEntry;

   public CommandProcessingSink(CommandManager commandManager, CommandProcessor commandProcessor) {
      this.commandManager = commandManager;
      this.commandProcessor = commandProcessor;
   }

   public void accept(String argument) {
      this.commandString.add(argument);
      if (!this.inputBuffer.isEmpty()) {
         this.inputBuffer.append(" ");
      }

      this.inputBuffer.append(argument);
      CommandData newCmdData = this.commandData == null
         ? this.commandManager.getCommandData(this.inputBuffer.toString())
         : this.commandData.findSubCommand(this.inputBuffer.toString());
      CommandData o = this.commandData;
      if (newCmdData != null) {
         this.inputBuffer = new StringBuilder();
         if (this.commandData == null) {
            this.arguments.clear();
         }

         this.commandData = newCmdData;
         this.candidates.put(newCmdData, new LinkedHashSet<>());

         for (CommandData.MethodData method : newCmdData.getMethods()) {
            this.candidates.get(newCmdData).add(new CommandProcessingSink.MethodProcessor(newCmdData, method));
         }
      }

      if (o != null) {
         this.arguments.add(argument);
      }
   }

   public CommandProcessingSink.ProcessedCommand finish(boolean furthestDepth) throws CommandException {
      if (this.commandData == null) {
         throw new UnknownCommandException("Unknown command: " + this.inputBuffer.toString());
      }

      CommandProcessingSink.MethodProcessor best = null;
      int depthOfBest = 0;

      for (CommandData commandData : this.candidates.keySet()) {
         for (CommandProcessingSink.MethodProcessor methodProcessor : this.candidates.get(commandData)) {
            this.currentEntry = methodProcessor;
            String[] methodArgs = this.arguments.subList(methodProcessor.argsDepth, this.arguments.size()).toArray(new String[0]);
            boolean gt = true;

            try {
               methodProcessor.process(methodArgs);
            } catch (Exception e) {
               if (e instanceof BadSyntaxException && e.getMessage().contains("few")) {
                  gt = false;
               }
            }

            boolean perfect = methodProcessor.getParsedArguments().size() == methodProcessor.getMethodData().arguments().size();
            int depth = methodProcessor.argsDepth + methodProcessor.getParsedArguments().size();
            if (best == null || (!gt && !furthestDepth ? depth > depthOfBest : depth >= depthOfBest) || perfect) {
               best = methodProcessor;
               depthOfBest = depth;
            }
         }
      }

      this.currentEntry = best;
      if (best == null) {
         throw new BadSyntaxException("Invalid syntax", this);
      } else if (best.err != null) {
         throw best.err;
      } else {
         return best.processed();
      }
   }

   public String getCommandString() {
      return this.commandString.toString();
   }

   public List<String> getArguments() {
      return this.arguments;
   }

   public CommandData getCommandData() {
      return this.commandData;
   }

   public CommandProcessingSink.MethodProcessor getCurrentEntry() {
      return this.currentEntry;
   }

   public class MethodProcessor {
      private final CommandData commandData;
      private final CommandData.MethodData methodData;
      private final int argsDepth;
      private final LinkedHashMap<CommandData.ArgumentData, Object> parsedArguments = new LinkedHashMap<>();
      private CommandException err = null;

      public MethodProcessor(CommandData commandData, CommandData.MethodData methodData) {
         this.commandData = commandData;
         this.methodData = methodData;
         int argOffset = 0;

         for (CommandData c = commandData; c.getParent() != null; c = c.getParent()) {
            argOffset++;
         }

         this.argsDepth = argOffset;
      }

      public void process(String... arguments) throws ArgumentException, BadSyntaxException {
         try {
            this.parsedArguments.clear();
            int requiredArgs = 0;

            for (CommandData.ArgumentData argData : this.methodData.arguments()) {
               if (!argData.optional()) {
                  requiredArgs++;
               }
            }

            int argIndex = 0;

            for (CommandData.ArgumentData argData : this.methodData.arguments()) {
               Class<?> argType = argData.type();
               boolean optional = argData.optional();
               Object parsedArg = null;

               try {
                  IArg<?> argParser = CommandProcessingSink.this.commandProcessor.getArgumentParser(argType);
                  if (argParser == null) {
                     throw new UnknownArgumentTypeException(String.format("Unknown argument type: \"%s\"", argType.getName()));
                  }

                  if (argParser.captureRemaining() && argIndex < arguments.length) {
                     String joinedArgs = String.join(" ", Arrays.copyOfRange(arguments, argIndex, arguments.length));
                     parsedArg = argParser.parse(CommandProcessingSink.this, joinedArgs);
                     argIndex = arguments.length;
                  } else {
                     String argString = arguments[argIndex];
                     parsedArg = argParser.parse(CommandProcessingSink.this, argString);
                     argIndex++;
                  }
               } catch (ArgumentException | IndexOutOfBoundsException e) {
                  if (!optional || e instanceof ArgumentException && argIndex >= requiredArgs) {
                     if (e instanceof IndexOutOfBoundsException) {
                        throw new BadSyntaxException(
                           String.format("Too few arguments (%s, expected %s)", arguments.length + this.argsDepth, requiredArgs + this.argsDepth),
                           CommandProcessingSink.this
                        );
                     }

                     throw e;
                  }
               }

               this.parsedArguments.put(argData, optional ? Optional.ofNullable(parsedArg) : parsedArg);
            }

            if (argIndex < arguments.length) {
               throw new BadSyntaxException(
                  String.format("Too many arguments (%s, expected %s)", arguments.length + this.argsDepth, requiredArgs + this.argsDepth),
                  CommandProcessingSink.this
               );
            }
         } catch (CommandException throwable) {
            this.err = throwable;
            throwable.setContext(CommandProcessingSink.this);
            throw throwable;
         }
      }

      public CommandProcessingSink.ProcessedCommand processed() {
         return new CommandProcessingSink.ProcessedCommand(this.commandData, this.methodData, this.parsedArguments);
      }

      public CommandException getError() {
         return this.err;
      }

      public CommandData getCommandData() {
         return this.commandData;
      }

      public CommandData.MethodData getMethodData() {
         return this.methodData;
      }

      public LinkedHashMap<CommandData.ArgumentData, Object> getParsedArguments() {
         return this.parsedArguments;
      }

      public int getBaseDepth() {
         return this.argsDepth;
      }
   }

   public record ProcessedCommand(CommandData commandData, CommandData.MethodData methodData, Map<CommandData.ArgumentData, Object> parsedArguments) {
   }
}
