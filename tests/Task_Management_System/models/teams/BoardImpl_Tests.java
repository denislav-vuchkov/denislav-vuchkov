package Task_Management_System.models.teams;

import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Task;
import Task_Management_System.models.teams.contracts.Board;
import Task_Management_System.models.teams.contracts.subcontracts.Nameable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static Task_Management_System.models.Factory.*;
import static Task_Management_System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task_Management_System.models.teams.contracts.subcontracts.Nameable.NAME_MAX_LEN;
import static Task_Management_System.models.teams.contracts.subcontracts.Nameable.NAME_MIN_LEN;

public class BoardImpl_Tests {

    private Board board;
    private Task bug;
    private Task story;
    private Task feedback;

    @BeforeEach
    public void setup() {
        board = new BoardImpl(VALID_BOARD_NAME);
        bug = createBug();
        story = createStory();
        feedback = createFeedback();
    }

    @Test
    public void constructor_Should_Initialise_When_ValidInput() {
        Assertions.assertDoesNotThrow(() -> new BoardImpl(VALID_BOARD_NAME));
        Assertions.assertTrue(board.getTasks().isEmpty() && board.getLog() != null);
    }

    @ParameterizedTest(name = "with length {0" +
            "" +
            "" +
            "}")
    @ValueSource(ints = {NAME_MIN_LEN -1, NAME_MAX_LEN +1})
    public void constructor_Should_ThrowException_When_InvalidName(int nameLength) {
        Assertions.assertThrows(RuntimeException.class, () -> new BoardImpl("x".repeat(nameLength)));
    }

    @Test
    public void addTask_Should_AddToCollection_When_ValidInput() {
        board.addTask(bug);
        board.addTask(story);
        board.addTask(feedback);

        Assertions.assertEquals(3, board.getTasks().size());
    }

    @Test
    public void addTask_Should_ThrowException_When_InvalidInput() {
        board.addTask(bug);

        Assertions.assertThrows(InvalidUserInput.class, () -> board.addTask(bug));
    }

    @Test
    public void removeTask_Should_ReduceCollection_When_ValidInput() {
        board.addTask(story);
        board.removeTask(story);

        Assertions.assertEquals(0, board.getTasks().size());
    }

    @Test
    public void removeTask_Should_ThrowException_When_InvalidInput() {
        board.addTask(story);

        Assertions.assertThrows(InvalidUserInput.class, () -> board.removeTask(feedback));
    }

    @Test
    public void getTasks_Should_ReturnCopyOfCollection() {
        board.addTask(feedback);

        List<Task> tasks = board.getTasks();
        tasks.add(story);

        Assertions.assertEquals(1, board.getTasks().size());
    }

    @Test
    public void BoardImpl_should_ImplementInterfaceBoardAndNameable() {
        BoardImpl board = new BoardImpl(VALID_BOARD_NAME);
        Assertions.assertTrue(board instanceof Board && board instanceof Nameable);
    }


}
