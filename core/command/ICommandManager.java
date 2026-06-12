package org.rusherhack.core.command;

import org.rusherhack.core.command.dispatch.ICommandDispatcher;
import org.rusherhack.core.command.processing.ICommandProcessor;
import org.rusherhack.core.feature.IFeatureManager;

public interface ICommandManager extends IFeatureManager<AbstractCommand> {
   ICommandProcessor getCommandProcessor();

   ICommandDispatcher<?> getDispatcher();

   String getPrefix();
}
