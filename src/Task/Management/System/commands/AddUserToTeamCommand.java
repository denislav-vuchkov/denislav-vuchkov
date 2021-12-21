package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AddUserToTeamCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String USER_DOES_NOT_EXIST = "User does not exist.";
    public static final String TEAM_DOES_NOT_EXIST = "Team does not exist.";

    public AddUserToTeamCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        User user;
        Team team;
        try {
            user = getRepository().findByName(getRepository().getUsers(), parameters.get(0));
        } catch (IllegalArgumentException userEx) {
            throw new NullPointerException(USER_DOES_NOT_EXIST);
        }
        try {
            team = getRepository().findByName(getRepository().getTeams(), parameters.get(1));
        } catch (IllegalArgumentException teamEx) {
            throw new NullPointerException(TEAM_DOES_NOT_EXIST);
        }
        team.addUser(user);
        return "Success";
    }
}
