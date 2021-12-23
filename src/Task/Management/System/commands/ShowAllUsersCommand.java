package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowAllUsersCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    public ShowAllUsersCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        List<User> users = getRepository().getUsers();
        if (users.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "users");
        }
        StringBuilder output = new StringBuilder();
        users.forEach(output::append);
        return output.toString().trim();
    }
}
