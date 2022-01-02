package Task_Management_System.commands.team;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.teams.BoardImpl;
import Task_Management_System.models.teams.contracts.Board;
import Task_Management_System.models.teams.contracts.Team;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;

public class CreateBoard extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String BOARD_CREATED_IN_TEAM = "Board %s successfully created in team %s.";
    public static final String BOARD_ALREADY_EXISTS = "Board %s already exists in team %s!";

    public CreateBoard(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(1));
        if (team.getBoards().stream().anyMatch(board -> board.getName().equals(parameters.get(0)))) {
            throw new InvalidUserInput(
                    String.format(BOARD_ALREADY_EXISTS, parameters.get(0), team.getName()));
        }
        Board board = new BoardImpl(parameters.get(0));
        team.addBoard(board);
        return String.format(BOARD_CREATED_IN_TEAM, board.getName(), team.getName());
    }
}
