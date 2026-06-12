package org.rusherhack.core.command.dispatch;

import org.rusherhack.core.command.ICommandSource;
import org.rusherhack.core.command.exceptions.CommandException;
import org.rusherhack.core.command.exceptions.DispatchException;

public interface ICommandDispatcher<T> {
   T execute(ICommandSource var1, String var2);

   T dispatch(ICommandSource var1, String var2) throws CommandException, DispatchException;
}
