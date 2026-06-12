package org.rusherhack.core.command.exceptions;

public class ArgumentException extends CommandException {
   public ArgumentException(String message) {
      super(message);
   }

   public ArgumentException(String message, Throwable cause) {
      super(message, cause);
   }

   @Override
   public boolean shouldPrintSyntax() {
      return this.getContext() != null;
   }
}
