package org.rusherhack.client.api.feature.command;

import org.rusherhack.client.api.Globals;
import org.rusherhack.core.command.AbstractCommand;

public abstract class Command extends AbstractCommand implements Globals {
   public Command(String name, String description) {
      super(name, description);
   }
}
