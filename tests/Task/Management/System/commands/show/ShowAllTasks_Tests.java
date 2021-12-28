package Task.Management.System.commands.show;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidNumberOfArguments;
import Task.Management.System.models.tasks.contracts.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task.Management.System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task.Management.System.models.TestData.FeedbackImpl.VALID_RATING;
import static Task.Management.System.models.TestData.TaskBase.VALID_DESCRIPTION;
import static Task.Management.System.models.TestData.TaskBase.VALID_TITLE;
import static Task.Management.System.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static Task.Management.System.models.TestData.UserImpl.VALID_USER_NAME;

public class ShowAllTasks_Tests {

    @Test
    public void showAllTasks_Should_throwException_WhenValidArguments() {
        CommandFactory commandFactory = new CommandFactoryImpl();
        TaskManagementSystemRepository repository = new TaskManagementSystemRepositoryImpl();
        Command command = commandFactory.createCommand("ShowAllTasks", repository);

        List<String> parameters = new ArrayList<>();
        parameters.add("Unnecessary parameter");

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> command.execute(parameters));
    }

    @Test
    public void showAllTasks_Should_Indicate_When_NoTasksToDisplay() {
        CommandFactory commandFactory = new CommandFactoryImpl();
        TaskManagementSystemRepository repository = new TaskManagementSystemRepositoryImpl();
        Command command = commandFactory.createCommand("ShowAllTasks", repository);

        Assertions.assertEquals(String.format(NO_ITEMS_TO_DISPLAY, "tasks"), command.execute(List.of()));
    }

    @Test
    public void showAllTasks_Should_Execute_When_ValidInput() {
        CommandFactory commandFactory = new CommandFactoryImpl();
        TaskManagementSystemRepository repository = new TaskManagementSystemRepositoryImpl();

        Command command = commandFactory.createCommand("ShowAllTasks", repository);

        Command createUser = commandFactory.createCommand("CreateUser", repository);
        createUser.execute(List.of(VALID_USER_NAME));

        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Command addUserToTeam = commandFactory.createCommand("AddUserToTeam", repository);
        addUserToTeam.execute(List.of(VALID_USER_NAME, VALID_TEAM_NAME));

        Command createBoard = commandFactory.createCommand("CreateBoard", repository);
        createBoard.execute(List.of(VALID_BOARD_NAME, VALID_TEAM_NAME));

        Command createFeedback = commandFactory.createCommand("CreateFeedback", repository);
        List<String> parameters = new ArrayList<>();
        parameters.add(VALID_USER_NAME);
        parameters.add(VALID_TEAM_NAME);
        parameters.add(VALID_BOARD_NAME);
        parameters.add(VALID_TITLE);
        parameters.add(VALID_DESCRIPTION);
        parameters.add(String.valueOf(VALID_RATING));

        createFeedback.execute(parameters);

        Feedback task = repository.findFeedback(1);

        String output = String.format("%s - ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                task.getClass().getSimpleName().replace("Impl", ""),
                task.getID(), task.getTitle(), task.getRating(), task.getStatus(), task.getComments().size());

        Assertions.assertEquals(output, command.execute(List.of()));
    }

}
