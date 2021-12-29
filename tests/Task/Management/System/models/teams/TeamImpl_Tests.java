package Task.Management.System.models.teams;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.subcontracts.Nameable;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static Task.Management.System.models.Factory.*;
import static Task.Management.System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task.Management.System.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static Task.Management.System.models.teams.contracts.subcontracts.Nameable.NAME_MAX_LEN;
import static Task.Management.System.models.teams.contracts.subcontracts.Nameable.NAME_MIN_LEN;

public class TeamImpl_Tests {

    private Team team;
    private Board board;
    private User user;

    @BeforeEach
    public void setup() {
        team = createTeam();
        board = createBoard();
        user = createUser();
    }

    @Test
    public void constructor_Should_Initialise_When_ValidInput() {
        Assertions.assertDoesNotThrow(() -> new TeamImpl(VALID_BOARD_NAME));
        Assertions.assertTrue(team.getBoards().size() == 0 &&
                team.getUsers().size() == 0 &&
                board.getLog() != null);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {NAME_MIN_LEN -1, NAME_MAX_LEN +1})
    public void constructor_Should_ThrowException_When_InvalidName(int nameLength) {
        Assertions.assertThrows(RuntimeException.class, () -> new TeamImpl("x".repeat(nameLength)));
    }

    @Test
    public void addBoard_Should_AddToCollection_When_ValidInput() {
        team.addBoard(board);

        Assertions.assertEquals(1, team.getBoards().size());
    }

    @Test
    public void addBoard_Should_ThrowException_When_InvalidInput() {
        team.addBoard(board);

        Assertions.assertThrows(InvalidUserInput.class, () -> team.addBoard(board));
    }

    @Test
    public void getBoards_Should_ReturnCopyOfCollection() {
        List<Board> boardList = team.getBoards();
        boardList.add(board);

        Assertions.assertEquals(0, team.getBoards().size());
    }

    @Test
    public void removeBoard_Should_ReduceCollection_When_ValidInput() {
        team.addBoard(board);
        team.removeBoard(board);

        Assertions.assertEquals(0, team.getBoards().size());
    }

    @Test
    public void removeBoard_Should_ThrowException_When_InvalidInput() {
        Assertions.assertThrows(InvalidUserInput.class, () -> team.removeBoard(board));
    }

    @Test
    public void addUser_Should_IncreaseCollection_When_ValidInput() {
        team.addUser(user);

        Assertions.assertEquals(1, team.getUsers().size());
    }

    @Test
    public void addUser_Should_ThrowException_When_InvalidInput() {
        team.addUser(user);

        Assertions.assertThrows(InvalidUserInput.class, () -> team.addUser(user));
    }

    @Test
    public void getUsers_Should_ReturnCopyOfCollection() {
        List<User> userList = team.getUsers();
        userList.add(user);

        Assertions.assertEquals(0, team.getUsers().size());
    }

    @Test
    public void removeUser_Should_ReduceCollection_When_ValidInput() {
        team.addUser(user);
        team.removeUser(user);

        Assertions.assertEquals(0, team.getUsers().size());
    }

    @Test
    public void removeUser_Should_ThrowException_When_InvalidInput() {
        Assertions.assertThrows(InvalidUserInput.class, () -> team.removeUser(user));
    }

    @Test
    public void toString_Should_PrintInValidFormat() {
        team.addBoard(board);
        team.addUser(user);

        String expectedOutput = String.format("Team: %s - Users: %d - Boards: %d - Tasks: %d",
                VALID_TEAM_NAME, 1, 1, 0);

        Assertions.assertEquals(expectedOutput, team.toString());
    }


    @Test
    public void BoardImpl_should_ImplementInterfaceBoardAndNameable() {
        TeamImpl board = new TeamImpl(VALID_BOARD_NAME);
        Assertions.assertTrue(team instanceof Team && board instanceof Nameable);
    }
}
