package Task.Management.System.commands.show;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

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
        return team.getBoards()
                .stream()
                .map(Board::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
