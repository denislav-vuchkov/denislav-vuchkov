package Task_Management_System.core.contracts;

import Task_Management_System.commands.contracts.Command;

public interface CommandFactory {

    Command createCommand(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository);

}
