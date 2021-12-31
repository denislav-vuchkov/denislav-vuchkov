package Task_Management_System.models.teams;

import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.AssignableTask;
import Task_Management_System.models.teams.contracts.subcontracts.Nameable;
import Task_Management_System.models.teams.contracts.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static Task_Management_System.models.Factory.createBug;
import static Task_Management_System.models.Factory.createStory;
import static Task_Management_System.models.TestData.UserImpl.VALID_USER_NAME;
import static Task_Management_System.models.teams.contracts.subcontracts.Nameable.NAME_MAX_LEN;
import static Task_Management_System.models.teams.contracts.subcontracts.Nameable.NAME_MIN_LEN;

public class UserImpl_Tests {

    private AssignableTask bug;
    private AssignableTask story;
    private User user;

    @BeforeEach
    public void setup() {
        bug = createBug();
        story = createStory();
        user = new UserImpl(VALID_USER_NAME);
    }

    @Test
    public void constructor_Should_Initialise_When_ValidInput() {
        Assertions.assertDoesNotThrow(() -> new UserImpl(VALID_USER_NAME));
        Assertions.assertTrue(user.getTasks().isEmpty() && user.getLog() != null);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {NAME_MIN_LEN - 1, NAME_MAX_LEN + 1})
    public void constructor_Should_ThrowException_When_InvalidName(int nameLength) {
        Assertions.assertThrows(RuntimeException.class, () -> new UserImpl("x".repeat(nameLength)));
    }

    @Test
    public void assignTask_should_AddToList_whenValidInput() {
        user.addTask(bug);
        user.addTask(story);

        Assertions.assertEquals(2, user.getTasks().size());
    }

    @Test
    public void assignTask_Should_ThrowException_WhenInvalidInput() {
        user.addTask(bug);

        Assertions.assertThrows(InvalidUserInput.class, () -> user.addTask(bug));
    }

    @Test
    public void getTask_should_ReturnCopyOfCollection() {
        user.addTask(bug);
        List<AssignableTask> tasks = user.getTasks();
        tasks.add(createStory());

        Assertions.assertEquals(1, user.getTasks().size());
    }

    @Test
    public void unAssignTask_should_RemoveFromCollection() {
        user.addTask(bug);
        user.addTask(story);

        user.removeTask(story);

        Assertions.assertEquals(1, user.getTasks().size());
    }

    @Test
    public void unAssignTask_Should_ThrowException_WhenInvalidInput() {
        user.addTask(bug);

        Assertions.assertThrows(InvalidUserInput.class, () -> user.removeTask(story));
    }

    @Test
    public void userImpl_should_ImplementInterface() {
        UserImpl user = new UserImpl(VALID_USER_NAME);
        Assertions.assertTrue(user instanceof User && user instanceof Nameable);
    }

    @Test
    public void toString_Should_ReturnValidOutput() {
        String expectedOutput = String.format("User: %s - Tasks: %d", VALID_USER_NAME, 0);
        Assertions.assertEquals(expectedOutput, user.toString());
    }


}
