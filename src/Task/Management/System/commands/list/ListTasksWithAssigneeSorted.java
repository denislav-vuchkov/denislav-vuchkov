package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;

import java.util.List;

public class ListTasksWithAssigneeSorted extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 666;

    public ListTasksWithAssigneeSorted(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //TODO Denis
        throw new InvalidUserInput("Command not implemented yet.");
    }
}
