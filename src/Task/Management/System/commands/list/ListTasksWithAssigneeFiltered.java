package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;

import java.util.List;

public class ListTasksWithAssigneeFiltered extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 666;

    public ListTasksWithAssigneeFiltered(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //TODO Denis
        throw new InvalidUserInput("Command not implemented yet.");
    }
}
