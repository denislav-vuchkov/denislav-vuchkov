package Task_Management_System.commands.team;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.teams.contracts.Team;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ValidationHelpers;

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

        User user = getRepository().findByName(getRepository().getUsers(), parameters.get(0), USER);

        Team team = getRepository().findByName(getRepository().getTeams(), parameters.get(1), TEAM);

        if (team.getUsers().contains(user)) {
            throw new InvalidUserInput(
                    String.format(USER_ALREADY_ON_TEAM, user.getName(), team.getName()));
        }

        team.addUser(user);

        return String.format(USER_ADDED_TO_TEAM, user.getName(), team.getName());
    }
}
