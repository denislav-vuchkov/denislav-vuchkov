package Task_Management_System.commands.tasks;

import Task_Management_System.commands.contracts.Command;
import Task_Management_System.core.CommandFactoryImpl;
import Task_Management_System.core.TaskManagementSystemRepositoryImpl;
import Task_Management_System.core.contracts.CommandFactory;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidNumberOfArguments;
import Task_Management_System.exceptions.InvalidUserInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeStory_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command changeStory;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        changeStory = cf.createCommand("ChangeStory", repo);
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

        cf.createCommand("CreateStory", repo).execute(List.of(
                "Denis",
                "Team1",
                "TaskManagement",
                "TestStoryTitle1",
                "DemoStoryDescription1",
                "High",
                "Small",
                "Tisho"));

        cf.createCommand("CreateFeedback", repo).execute(List.of(
                "Tisho",
                "Team1",
                "TaskManagement",
                "test feedback name",
                "test feedback description",
                "5"));

        parameters.add("Tisho");
        parameters.add("1");
        parameters.add("priority");
        parameters.add("medium");
    }

    @Test
    public void ChangeStory_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.remove(0);
        parameters.remove(1);
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> changeStory.execute(parameters));
    }

    @Test
    public void ChangeStory_ShouldThrowException_ifChangerInvalid() {
        parameters.set(0, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeStory.execute(parameters));
    }

    @Test
    public void ChangeStory_ShouldThrowException_ifForeignChanger() {
        parameters.set(0, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeStory.execute(parameters));
    }

    @Test
    public void ChangeStory_ShouldThrowException_ifTaskInvalid() {
        parameters.set(1, "-2");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeStory.execute(parameters));
    }

    @Test
    public void ChangeStory_ShouldThrowException_ifTaskWrong() {
        parameters.set(1, "2");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeStory.execute(parameters));
    }

    @Test
    public void ChangeStory_ShouldThrowException_ifPropertyInvalid() {
        parameters.set(2, "title");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeStory.execute(parameters));
    }

    @Test
    public void ChangeStory_ShouldChangePriority_ifNewValueValid() {
        Assertions.assertEquals(
                "User Tisho: Successfully changed the PRIORITY of Story with ID 1 to MEDIUM.",
                changeStory.execute(parameters));
        Assertions.assertEquals("Medium", repo.findStory(1).getPriority().toString());
    }

    @Test
    public void ChangeStory_ShouldChangeSize_ifNewValueValid() {
        parameters.set(2, "size");
        parameters.set(3, "large");
        Assertions.assertEquals(
                "User Tisho: Successfully changed the SIZE of Story with ID 1 to LARGE.",
                changeStory.execute(parameters));
        Assertions.assertEquals("Large", repo.findStory(1).getSize().toString());
    }

    @Test
    public void ChangeStory_ShouldChangeStatus_ifNewValueValid() {
        parameters.set(2, "status");
        parameters.set(3, "done");
        Assertions.assertEquals(
                "User Tisho: Successfully changed the STATUS of Story with ID 1 to DONE.",
                changeStory.execute(parameters));
        Assertions.assertEquals("Done", repo.findStory(1).getStatus().toString());
    }
}

