package org.rusherhack.core.command.argument.parser.impl;

import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.processing.CommandProcessingSink;

public class DoubleArg implements IArg<Double> {
   public Double parse(CommandProcessingSink context, String arg) throws ArgumentException {
      try {
         return Double.parseDouble(arg);
      } catch (Throwable e) {
         throw new ArgumentException("Invalid number");
      }
   }
}
