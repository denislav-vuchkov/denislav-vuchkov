package Task_Management_System.commands.team;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;

public class CreateUser extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public CreateUser(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String userName = parameters.get(0);
        ValidationHelpers.validateUniqueName(userName, getRepository().getUsers(), USER);
        return getRepository().addUser(userName);
    }
}
