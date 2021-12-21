package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AddUserToTeamCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String USER_ADDED_TO_TEAM = "User %s successfully added to team %s.";
    public static final String USER_ALREADY_ON_TEAM = "User %s is already part of team %s!";

    public AddUserToTeamCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository()
                .getUsers()
                .stream()
                .filter(u -> u.getName().equals(parameters.get(0)))
                .findAny()
                .orElseThrow(() -> new NullPointerException(USER_DOES_NOT_EXIST));

        Team team = getRepository()
                .getTeams()
                .stream()
                .filter(t -> t.getName().equals(parameters.get(1)))
                .findAny()
                .orElseThrow(() -> new NullPointerException(TEAM_DOES_NOT_EXIST));

        if (team.getUsers().contains(user)) {
            throw new IllegalArgumentException(String.format(USER_ALREADY_ON_TEAM, user.getName(), team.getName()));
        }

        team.addUser(user);
        return String.format(USER_ADDED_TO_TEAM, user.getName(), team.getName());
    }
}
