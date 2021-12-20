package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeamsCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    public static final String TEAMS_HEADER = "-=TEAMS=-";
    public static final String TEAMS_EMPTY = "No teams to display.";

    public ShowAllTeamsCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        List<Team> teams = getTaskManagementSystemRepository().getTeams();
        if (teams.isEmpty()) {
            return TEAMS_EMPTY;
        }
        StringBuilder output = new StringBuilder(TEAMS_HEADER);
        output.append(System.lineSeparator());
        teams.forEach(output::append);
        return output.toString().trim();
    }
}
