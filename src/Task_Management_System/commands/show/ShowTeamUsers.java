package Task_Management_System.commands.show;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.teams.contracts.Team;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTeamUsers extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTeamUsers(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(0));
        if (team.getUsers().isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "users");
        }

        return team.getUsers()
                .stream()
                .map(User::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
