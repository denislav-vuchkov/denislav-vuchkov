package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public class CreateNewStoryInBoardCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 666;

    public CreateNewStoryInBoardCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
