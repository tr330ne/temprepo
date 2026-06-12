package org.rusherhack.core.command.argument.parser.impl;

import org.rusherhack.core.command.argument.StringCapture;
import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.processing.CommandProcessingSink;

public class StringCapturerArg implements IArg<StringCapture> {
   public StringCapture parse(CommandProcessingSink context, String str) throws ArgumentException {
      return new StringCapture(str.trim());
   }

   @Override
   public boolean captureRemaining() {
      return true;
   }
}
