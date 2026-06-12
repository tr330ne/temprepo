package org.rusherhack.core.command.exceptions;

public class DispatchException extends Exception {
   public DispatchException(String message) {
      super(message);
   }

   public DispatchException(String message, Throwable cause) {
      super(message, cause);
   }
}
