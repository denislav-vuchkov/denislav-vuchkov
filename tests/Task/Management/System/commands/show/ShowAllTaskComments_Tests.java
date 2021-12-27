package Task.Management.System.commands.show;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidNumberOfArguments;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task.Management.System.commands.show.ShowTaskComments.EXPECTED_NUMBER_OF_ARGUMENTS;
import static Task.Management.System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task.Management.System.models.TestData.FeedbackImpl.VALID_RATING;
import static Task.Management.System.models.TestData.TaskBase.VALID_DESCRIPTION;
import static Task.Management.System.models.TestData.TaskBase.VALID_TITLE;
import static Task.Management.System.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static Task.Management.System.models.TestData.UserImpl.VALID_USER_NAME;
import static Task.Management.System.models.tasks.CommentImpl.CONTENT_LEN_MIN;

public class ShowAllTaskComments_Tests {


    @ParameterizedTest (name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void showTaskComments_Should_throwException_WhenValidArguments(int parametersCount) {
        CommandFactory commandFactory = new CommandFactoryImpl();
        TaskManagementSystemRepository repository = new TaskManagementSystemRepositoryImpl();
        Command command = commandFactory.createCommandFromCommandName("ShowTaskComments", repository);

        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> command.execute(parameters));
    }

    @Test
    public void showTaskComments_Should_Indicate_When_NoCommentsToDisplay() {
        CommandFactory commandFactory = new CommandFactoryImpl();
        TaskManagementSystemRepository repository = new TaskManagementSystemRepositoryImpl();
        Command command = commandFactory.createCommandFromCommandName("ShowTaskComments", repository);

        Command createUser = commandFactory.createCommandFromCommandName("CreateUser", repository);
        createUser.execute(List.of(VALID_USER_NAME));

        Command createTeam = commandFactory.createCommandFromCommandName("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Command addUserToTeam = commandFactory.createCommandFromCommandName("AddUserToTeam", repository);
        addUserToTeam.execute(List.of(VALID_USER_NAME, VALID_TEAM_NAME));

        Command createBoard = commandFactory.createCommandFromCommandName("CreateBoard", repository);
        createBoard.execute(List.of(VALID_BOARD_NAME, VALID_TEAM_NAME));

        Command createFeedback = commandFactory.createCommandFromCommandName("CreateFeedback", repository);
        List<String> parameters = new ArrayList<>();
        parameters.add(VALID_USER_NAME);
        parameters.add(VALID_TEAM_NAME);
        parameters.add(VALID_BOARD_NAME);
        parameters.add(VALID_TITLE);
        parameters.add(VALID_DESCRIPTION);
        parameters.add(String.valueOf(VALID_RATING));
        createFeedback.execute(parameters);

        Assertions.assertEquals(String.format(NO_ITEMS_TO_DISPLAY, "comments"), command.execute(List.of("1")));
    }

    @Test
    public void showTaskComments_Should_Execute_When_ValidInput() {
        CommandFactory commandFactory = new CommandFactoryImpl();
        TaskManagementSystemRepository repository = new TaskManagementSystemRepositoryImpl();
        Command command = commandFactory.createCommandFromCommandName("ShowTaskComments", repository);

        Command createUser = commandFactory.createCommandFromCommandName("CreateUser", repository);
        createUser.execute(List.of(VALID_USER_NAME));

        Command createTeam = commandFactory.createCommandFromCommandName("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Command addUserToTeam = commandFactory.createCommandFromCommandName("AddUserToTeam", repository);
        addUserToTeam.execute(List.of(VALID_USER_NAME, VALID_TEAM_NAME));

        Command createBoard = commandFactory.createCommandFromCommandName("CreateBoard", repository);
        createBoard.execute(List.of(VALID_BOARD_NAME, VALID_TEAM_NAME));

        Command createFeedback = commandFactory.createCommandFromCommandName("CreateFeedback", repository);
        List<String> parameters = new ArrayList<>();
        parameters.add(VALID_USER_NAME);
        parameters.add(VALID_TEAM_NAME);
        parameters.add(VALID_BOARD_NAME);
        parameters.add(VALID_TITLE);
        parameters.add(VALID_DESCRIPTION);
        parameters.add(String.valueOf(VALID_RATING));
        createFeedback.execute(parameters);


        Command addComment = commandFactory.createCommandFromCommandName("AddCommentToTask", repository);
        List<String> commentParameters = new ArrayList<>();
        commentParameters.add(VALID_USER_NAME);
        commentParameters.add("1");
        commentParameters.add("x".repeat(CONTENT_LEN_MIN));
        addComment.execute(commentParameters);

        Feedback task = repository.findFeedback(1);
        List<Comment> commentList = task.getComments();

        String output = String.format("Author: %s - Comment: %s", commentList.get(0).getAuthor(), commentList.get(0).getContent());

        Assertions.assertEquals(output, command.execute(List.of("1")));
    }

}
