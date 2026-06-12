package org.rusherhack.core.command.processing;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.rusherhack.core.command.CommandManager;
import org.rusherhack.core.command.CommandUtils;
import org.rusherhack.core.command.argument.StringCapture;
import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.argument.parser.impl.BooleanArg;
import org.rusherhack.core.command.argument.parser.impl.ColorArg;
import org.rusherhack.core.command.argument.parser.impl.DoubleArg;
import org.rusherhack.core.command.argument.parser.impl.EnumArg;
import org.rusherhack.core.command.argument.parser.impl.IntegerArg;
import org.rusherhack.core.command.argument.parser.impl.StringArg;
import org.rusherhack.core.command.argument.parser.impl.StringCapturerArg;
import org.rusherhack.core.command.exceptions.CommandException;

public class CommandProcessor implements ICommandProcessor {
   private final Map<Predicate<Class<?>>, IArg<?>> argumentParserMap = new HashMap<>();

   public CommandProcessor() {
      this.addArgumentParser(Boolean.class, new BooleanArg());
      this.addArgumentParser(boolean.class, new BooleanArg());
      this.addArgumentParser(Double.class, new DoubleArg());
      this.addArgumentParser(double.class, new DoubleArg());
      this.addArgumentParser(Integer.class, new IntegerArg());
      this.addArgumentParser(int.class, new IntegerArg());
      this.addArgumentParser(String.class, new StringArg());
      this.addArgumentParser(Class::isEnum, new EnumArg());
      this.addArgumentParser(Color.class, new ColorArg());
      this.addArgumentParser(StringCapture.class, new StringCapturerArg());
   }

   public CommandProcessingSink processCommand(CommandManager commandManager, String input) throws CommandException {
      CommandProcessingSink sink = new CommandProcessingSink(commandManager, this);

      for (String arg : CommandUtils.processArguments(input, true)) {
         sink.accept(arg);
      }

      return sink;
   }

   @Override
   public void addArgumentParser(Class<?> typeClass, IArg<?> parser) {
      this.argumentParserMap.put(clazz -> clazz.equals(typeClass), parser);
   }

   @Override
   public void addArgumentParser(Predicate<Class<?>> predicate, IArg<?> parser) {
      this.argumentParserMap.put(predicate, parser);
   }

   @Override
   public IArg<?> getArgumentParser(Class<?> typeClass) {
      for (Entry<Predicate<Class<?>>, IArg<?>> entry : this.argumentParserMap.entrySet()) {
         if (entry.getKey().test(typeClass)) {
            return entry.getValue();
         }
      }

      return null;
   }
}
