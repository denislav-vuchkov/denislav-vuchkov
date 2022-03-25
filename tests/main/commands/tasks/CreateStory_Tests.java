package main.commands.tasks;

import main.commands.contracts.Command;
import main.core.CommandFactoryImpl;
import main.core.TaskManagementSystemRepositoryImpl;
import main.core.contracts.CommandFactory;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidNumberOfArguments;
import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateStory_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command createStory;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        createStory = cf.createCommand("CreateStory", repo);
        parameters = new ArrayList<>();

        cf.createCommand("CreateTeam", repo).execute(List.of("Team1"));
        cf.createCommand("CreateUser", repo).execute(List.of("Denis"));
        cf.createCommand("CreateUser", repo).execute(List.of("Tisho"));

        cf.createCommand("AddUserToTeam", repo).execute(List.of("Denis", "Team1"));
        cf.createCommand("AddUserToTeam", repo).execute(List.of("Tisho", "Team1"));
        cf.createCommand("CreateBoard", repo).execute(List.of("TaskManagement", "Team1"));

        cf.createCommand("CreateTeam", repo).execute(List.of("Others"));
        cf.createCommand("CreateUser", repo).execute(List.of("Outsider"));
        cf.createCommand("AddUserToTeam", repo).execute(List.of("Outsider", "Others"));
        cf.createCommand("CreateBoard", repo).execute(List.of("EvilBoard", "Others"));

        parameters.add("Denis");
        parameters.add("Team1");
        parameters.add("TaskManagement");
        parameters.add("Activity Log Is Incomplete");
        parameters.add("The activity report needs to show more stuff");
        parameters.add("High");
        parameters.add("Medium");
        parameters.add("Tisho");

    }

    @Test
    public void CreateStory_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.remove(0);
        parameters.remove(1);
        parameters.remove(2);
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifCreatorInvalid() {
        parameters.set(0, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifForeignCreator() {
        parameters.set(0, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifTeamInvalid() {
        parameters.set(1, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifForeignTeam() {
        parameters.set(1, "Others");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifBoardInvalid() {
        parameters.set(2, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifForeignBoard() {
        parameters.set(2, "EvilBoard");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifPriorityInvalid() {
        parameters.set(5, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifSizeInvalid() {
        parameters.set(6, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifAssigneeInvalid() {
        parameters.set(7, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ShouldThrowException_ifForeignAssignee() {
        parameters.set(7, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> createStory.execute(parameters));
    }

    @Test
    public void CreateStory_ReturnsCorrectString_WithValidParameters() {
        Assertions.assertEquals(0, repo.getStories().size());
        Assertions.assertEquals(
                "Story with ID 1 successfully added to board TaskManagement in team Team1.",
                createStory.execute(parameters));
        Assertions.assertEquals(1, repo.getStories().size());
        Story story = repo.findStory(1);
        Assertions.assertEquals("Activity Log Is Incomplete", story.getTitle());
        Assertions.assertEquals("Medium", story.getSize().toString());
    }
}
