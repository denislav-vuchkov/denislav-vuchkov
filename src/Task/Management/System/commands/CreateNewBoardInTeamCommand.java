package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public class CreateNewBoardInTeamCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public CreateNewBoardInTeamCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
