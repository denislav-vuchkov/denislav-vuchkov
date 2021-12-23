package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamBoardsCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTeamBoardsCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(0));
        if (team.getBoards().isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "boards");
        }
        StringBuilder output = new StringBuilder();
        team.getBoards().forEach(output::append);
        return output.toString().trim();
    }
}
