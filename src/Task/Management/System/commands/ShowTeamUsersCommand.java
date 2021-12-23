package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamUsersCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTeamUsersCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(0));
        if (team.getUsers().isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "users");
        }
        StringBuilder output = new StringBuilder();
        team.getBoards().forEach(output::append);
        return output.toString().trim();
    }
}
