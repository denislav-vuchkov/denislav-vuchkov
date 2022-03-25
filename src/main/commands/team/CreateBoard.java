package main.commands.team;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidUserInput;
import main.models.teams.BoardImpl;
import main.models.teams.contracts.Board;
import main.models.teams.contracts.Team;
import main.utils.ValidationHelpers;

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
