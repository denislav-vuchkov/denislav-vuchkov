package Task.Management.System.core.contracts;

import Task.Management.System.commands.contracts.Command;

public interface CommandFactory {

    Command createCommandFromCommandName(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository);

}
