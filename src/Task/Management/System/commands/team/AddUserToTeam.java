package Task.Management.System.commands.team;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AddUserToTeam extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String USER_ADDED_TO_TEAM = "User %s successfully added to team %s.";
    public static final String USER_ALREADY_ON_TEAM = "User %s is already part of team %s!";

    public AddUserToTeam(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0));

        Team team = getRepository().findTeam(parameters.get(1));

        if (team.getUsers().contains(user)) {
            throw new InvalidUserInput(
                    String.format(USER_ALREADY_ON_TEAM, user.getName(), team.getName()));
        }

        team.addUser(user);

        return String.format(USER_ADDED_TO_TEAM, user.getName(), team.getName());
    }
}
