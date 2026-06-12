package org.rusherhack.core.command.exceptions;

import org.rusherhack.core.command.processing.CommandProcessingSink;

public class CommandException extends Exception {
   private CommandProcessingSink context = null;

   public CommandException(String message) {
      super(message);
   }

   public CommandException(String message, Throwable cause) {
      super(message, cause);
   }

   public CommandException(String message, CommandProcessingSink context) {
      super(message);
      this.context = context;
   }

   public CommandException(String message, CommandProcessingSink context, Throwable cause) {
      super(message, cause);
      this.context = context;
   }

   public void setContext(CommandProcessingSink context) {
      this.context = context;
   }

   public CommandProcessingSink getContext() {
      return this.context;
   }

   public boolean shouldPrintSyntax() {
      return false;
   }
}
