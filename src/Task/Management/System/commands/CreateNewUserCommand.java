package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.UserImpl;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateNewUserCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String USER_ADDED_SUCCESSFULLY = "User %s created successfully.";

    public CreateNewUserCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        if (getRepository().getUsers().stream().anyMatch(user -> user.getName().equals(parameters.get(0)))) {
            throw new IllegalArgumentException(USER_ALREADY_EXISTS);
        }
        User user = new UserImpl(parameters.get(0));
        getRepository().addUser(user);
        return String.format(USER_ADDED_SUCCESSFULLY, user.getName());
    }
}
