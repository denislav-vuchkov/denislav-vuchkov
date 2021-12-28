package Task.Management.System.commands.show;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidNumberOfArguments;
import Task.Management.System.models.teams.contracts.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task.Management.System.commands.show.ShowTeamBoards.EXPECTED_NUMBER_OF_ARGUMENTS;
import static Task.Management.System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task.Management.System.models.TestData.TeamImpl.VALID_TEAM_NAME;

public class ShowAllTeamBoards_Tests {

    CommandFactory commandFactory;
    TaskManagementSystemRepository repository;
    Command command;

    @BeforeEach
    public void setup() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        command = commandFactory.createCommand("ShowTeamBoards", repository);

    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void showTeamBoards_Should_throwException_WhenValidArguments(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> command.execute(parameters));
    }

    @Test
    public void showTeamBoards_Should_Indicate_When_NoCommentsToDisplay() {
        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Assertions.assertEquals(String.format(NO_ITEMS_TO_DISPLAY, "boards"), command.execute(List.of(VALID_TEAM_NAME)));
    }

    @Test
    public void showTeamBoards_Should_Execute_When_ValidInput() {
        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Command createBoard = commandFactory.createCommand("CreateBoard", repository);
        createBoard.execute(List.of(VALID_BOARD_NAME, VALID_TEAM_NAME));

        Board board = repository.findBoard(VALID_BOARD_NAME, VALID_TEAM_NAME);

        String output = String.format("Board name: %s - Board items: %d", board.getName(), board.getTasks().size());

        Assertions.assertEquals(output, command.execute(List.of(VALID_BOARD_NAME)));
    }

}
