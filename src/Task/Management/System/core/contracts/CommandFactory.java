package Task.Management.System.core.contracts;

import Task.Management.System.commands.contracts.Command;

public interface CommandFactory {

    Command createCommand(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository);

}
