package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowAllUsersInTeamCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_USERS_HEADER = "--NO USERS IN TEAM %s--";
    public static final String USERS_HEADER = "--USERS IN TEAM: %s";

    public ShowAllUsersInTeamCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        Team team = getRepository().findTeam(parameters.get(0));

        StringBuilder result = new StringBuilder();

        if (team.getUsers().isEmpty()) {
            result.append(String.format(NO_USERS_HEADER, parameters.get(0)));
        } else {
            result.append(String.format(USERS_HEADER, parameters.get(0))).append("\n");
            team.getUsers().forEach(e -> result.append(e.toString()).append("\n"));
            result.append(String.format(USERS_HEADER, parameters.get(0)));
        }

        return result.toString();
    }
}
