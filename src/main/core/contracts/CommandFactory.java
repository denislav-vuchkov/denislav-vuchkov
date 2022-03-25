package main.core.contracts;

import main.commands.contracts.Command;

public interface CommandFactory {

    Command createCommand(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository);

}
