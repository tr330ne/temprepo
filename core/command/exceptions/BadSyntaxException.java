package org.rusherhack.core.command.exceptions;

import org.rusherhack.core.command.processing.CommandProcessingSink;

public class BadSyntaxException extends CommandException {
   public BadSyntaxException(String message) {
      super("Syntax error: " + message);
   }

   public BadSyntaxException(String message, CommandProcessingSink context) {
      super("Syntax error: " + message, context);
   }

   @Override
   public boolean shouldPrintSyntax() {
      return this.getContext() != null;
   }
}
