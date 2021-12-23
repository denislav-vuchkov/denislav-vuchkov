package Task.Management.System.commands.team;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.BoardImpl;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateBoardCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String BOARD_CREATED_IN_TEAM = "Board %s successfully created in team %s.";
    public static final String BOARD_ALREADY_EXISTS = "Board %s already exists in team %s!";

    public CreateBoardCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(1));

        if (team.getBoards().stream().anyMatch(b -> b.getName().equals(parameters.get(0)))) {
            throw new IllegalArgumentException(
                    String.format(BOARD_ALREADY_EXISTS, parameters.get(0), team.getName()));
        }

        Board board = new BoardImpl(parameters.get(0));
        team.addBoard(board);
        return String.format(BOARD_CREATED_IN_TEAM, board.getName(), team.getName());
    }
}
