package main.commands.team;

import main.commands.contracts.Command;
import main.core.CommandFactoryImpl;
import main.core.TaskManagementSystemRepositoryImpl;
import main.core.contracts.CommandFactory;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidNumberOfArguments;
import main.exceptions.InvalidUserInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static main.commands.BaseCommand.UNASSIGNED;
import static main.commands.team.AssignTask.EXPECTED_NUMBER_OF_ARGUMENTS;
import static main.models.TestData.AssignableTask.VALID_PRIORITY;
import static main.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static main.models.TestData.BugImpl.VALID_SEVERITY;
import static main.models.TestData.TaskBase.VALID_DESCRIPTION;
import static main.models.TestData.TaskBase.VALID_TITLE;
import static main.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static main.models.TestData.UserImpl.VALID_USER_NAME;
import static main.models.teams.contracts.subcontracts.Nameable.NAME_MAX_LEN;
import static main.models.TestData.StoryImpl.VALID_SIZE;

public class AssignTask_Tests {

    CommandFactory commandFactory;
    TaskManagementSystemRepository repository;
    Command assignTask;
    String oldAssignee = VALID_USER_NAME;
    String newAssignee = "t".repeat(NAME_MAX_LEN -1);

    @BeforeEach
    public void setup() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        assignTask = commandFactory.createCommand("AssignTask", repository);

        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Command createUser = commandFactory.createCommand("CreateUser", repository);
        createUser.execute(List.of(oldAssignee));
        createUser.execute(List.of(newAssignee));

        Command addUserToTeam = commandFactory.createCommand("AddUserToTeam", repository);
        addUserToTeam.execute(List.of(oldAssignee, VALID_TEAM_NAME));
        addUserToTeam.execute(List.of(newAssignee, VALID_TEAM_NAME));

        Command createBoard = commandFactory.createCommand("CreateBoard", repository);
        createBoard.execute(List.of(VALID_BOARD_NAME, VALID_TEAM_NAME));

        Command createBug = commandFactory.createCommand("CreateBug", repository);
        List<String> parameters = new ArrayList<>();
        parameters.add(oldAssignee);
        parameters.add(VALID_TEAM_NAME);
        parameters.add(VALID_BOARD_NAME);
        parameters.add(VALID_TITLE);
        parameters.add(VALID_DESCRIPTION);
        parameters.add("Step 1;Step 2;Step 3");
        parameters.add(VALID_PRIORITY.toString());
        parameters.add(VALID_SEVERITY.toString());
        parameters.add(oldAssignee);
        createBug.execute(parameters);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void assignTask_Should_throwException_WhenValidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> assignTask.execute(parameters));
        Assertions.assertEquals(0, getCountOfUnassignedTasks());
    }

    @Test
    public void assignTask_Should_Execute_WhenValidInput() {
        List<String> parameters = List.of(oldAssignee, "1", newAssignee);

        Assertions.assertDoesNotThrow(() -> assignTask.execute(parameters));
        Assertions.assertEquals(0, repository.findUser(oldAssignee).getTasks().size());
        Assertions.assertEquals(1, repository.findUser(newAssignee).getTasks().size());
        Assertions.assertEquals(0, getCountOfUnassignedTasks());
    }

    @Test
    public void assignTask_Should_ThrowException_WhenAssignerNotInSameTeamAsTask() {
        Command createUser = commandFactory.createCommand("CreateUser", repository);
        String invalidUser = "Tihomir";
        createUser.execute(List.of(invalidUser));

        List<String> parameters = List.of(invalidUser, "1", newAssignee);

        Assertions.assertThrows(InvalidUserInput.class, () -> assignTask.execute(parameters));
        Assertions.assertEquals(1, repository.findUser(oldAssignee).getTasks().size());
        Assertions.assertEquals(0, repository.findUser(invalidUser).getTasks().size());
        Assertions.assertEquals(0, repository.findUser(newAssignee).getTasks().size());
        Assertions.assertEquals(0, getCountOfUnassignedTasks());
    }

    @Test
    public void assignTask_Should_ThrowException_WhenNewAssigneeNotInSameTeamAsTask() {
        Command createUser = commandFactory.createCommand("CreateUser", repository);
        String invalidUser = "Tihomir";
        createUser.execute(List.of(invalidUser));

        List<String> parameters = List.of(oldAssignee, "1", invalidUser);

        Assertions.assertThrows(InvalidUserInput.class, () -> assignTask.execute(parameters));
        Assertions.assertEquals(1, repository.findUser(oldAssignee).getTasks().size());
        Assertions.assertEquals(0, repository.findUser(invalidUser).getTasks().size());
        Assertions.assertEquals(0, repository.findUser(newAssignee).getTasks().size());
        Assertions.assertEquals(0, getCountOfUnassignedTasks());
    }

    @Test
    public void assignTask_Should_ThrowException_WhenCurrentAndNewAssigneeAreIdentical() {
        List<String> parameters = List.of(oldAssignee, "1", oldAssignee);

        Assertions.assertThrows(InvalidUserInput.class, () -> assignTask.execute(parameters));
        Assertions.assertEquals(1, repository.findUser(oldAssignee).getTasks().size());
        Assertions.assertEquals(0, repository.findUser(newAssignee).getTasks().size());
        Assertions.assertEquals(0, getCountOfUnassignedTasks());
    }

    @Test
    public void assignTask_Should_Execute_WhenReassigningUnassignedTask() {
        //Saving the value under 'assigner' as the role of the user in this test is to be an assigner
        String assigner = oldAssignee;

        //Creating bug without value for assignee which should initiate it with 'Unassigned'
        Command createBug = commandFactory.createCommand("CreateBug", repository);
        List<String> bugParameters = new ArrayList<>();
        bugParameters.add(oldAssignee);
        bugParameters.add(VALID_TEAM_NAME);
        bugParameters.add(VALID_BOARD_NAME);
        bugParameters.add(VALID_TITLE);
        bugParameters.add(VALID_DESCRIPTION);
        bugParameters.add("Step 1;Step 2;Step 3");
        bugParameters.add(VALID_PRIORITY.toString());
        bugParameters.add(VALID_SEVERITY.toString());
        bugParameters.add("");
        createBug.execute(bugParameters);

        List<String> parameters = List.of(assigner, "2", newAssignee);

        Assertions.assertDoesNotThrow(() -> assignTask.execute(parameters));
        Assertions.assertEquals(1, repository.findUser(newAssignee).getTasks().size());
        Assertions.assertEquals(0, getCountOfUnassignedTasks());
    }

    public int getCountOfUnassignedTasks() {
        return (int) repository.getAssignableTasks()
                .stream()
                .filter(e -> e.getAssignee().equals(UNASSIGNED))
                .count();
    }

    @Test
    public void assignTask_Should_Execute_WithMultipleTasks() {
        //Saving the value under 'assigner' as the role of the user in this test is to be an assigner
        String assigner = oldAssignee;

        //Creating story without value for assignee which should initiate it with 'Unassigned'
        Command createStory = commandFactory.createCommand("CreateStory", repository);
        List<String> storyParameters = new ArrayList<>();
        storyParameters.add(oldAssignee);
        storyParameters.add(VALID_TEAM_NAME);
        storyParameters.add(VALID_BOARD_NAME);
        storyParameters.add(VALID_TITLE);
        storyParameters.add(VALID_DESCRIPTION);
        storyParameters.add(VALID_PRIORITY.toString());
        storyParameters.add(VALID_SIZE.toString());
        storyParameters.add(oldAssignee);
        createStory.execute(storyParameters);
        storyParameters.set(7,"  ");
        createStory.execute(storyParameters);

        List<String> parameters = List.of(assigner, "2;1;3", newAssignee);
        Assertions.assertDoesNotThrow(() -> assignTask.execute(parameters));
        Assertions.assertEquals(3, repository.findUser(newAssignee).getTasks().size());
        Assertions.assertEquals(0, getCountOfUnassignedTasks());
    }


    @Test
    public void assignTask_Should_Unassign_WithEmptyAsigneeOnMultipleTasks() {
        //Saving the value under 'assigner' as the role of the user in this test is to be an assigner
        String assigner = oldAssignee;

        //Creating story without value for assignee which should initiate it with 'Unassigned'
        Command createStory = commandFactory.createCommand("CreateStory", repository);
        List<String> storyParameters = new ArrayList<>();
        storyParameters.add(oldAssignee);
        storyParameters.add(VALID_TEAM_NAME);
        storyParameters.add(VALID_BOARD_NAME);
        storyParameters.add(VALID_TITLE);
        storyParameters.add(VALID_DESCRIPTION);
        storyParameters.add(VALID_PRIORITY.toString());
        storyParameters.add(VALID_SIZE.toString());
        storyParameters.add(oldAssignee);
        createStory.execute(storyParameters);
        storyParameters.set(7,newAssignee);
        createStory.execute(storyParameters);

        List<String> parameters = List.of(assigner, "3;1;2", "   ");
        Assertions.assertDoesNotThrow(() -> assignTask.execute(parameters));
        Assertions.assertEquals(3, getCountOfUnassignedTasks());
    }


}
