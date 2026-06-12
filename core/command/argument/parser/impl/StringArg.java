package org.rusherhack.core.command.argument.parser.impl;

import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.processing.CommandProcessingSink;

public class StringArg implements IArg<String> {
   public String parse(CommandProcessingSink context, String arg) throws ArgumentException {
      if (arg == null) {
         throw new ArgumentException("Null string value");
      } else {
         return arg;
      }
   }
}
