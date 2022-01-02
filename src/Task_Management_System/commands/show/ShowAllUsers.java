package Task_Management_System.commands.show;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowAllUsers extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    public ShowAllUsers(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        List<User> users = getRepository().getUsers();
        if (users.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "users");
        }

        return users
                .stream()
                .map(User::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
