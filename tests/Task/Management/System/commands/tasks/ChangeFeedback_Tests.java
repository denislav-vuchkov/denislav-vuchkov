package Task.Management.System.commands.tasks;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidNumberOfArguments;
import Task.Management.System.exceptions.InvalidUserInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeFeedback_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command changeFeedback;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        changeFeedback = cf.createCommand("ChangeFeedback", repo);
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

        cf.createCommand("CreateBug", repo).execute(List.of(
                "Tisho",
                "Team1",
                "TaskManagement",
                "TestBugTitle1",
                "DemoBugDescription1",
                "One;Two;Three",
                "Low",
                "Critical",
                "Denis"));

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

        parameters.add("Denis");
        parameters.add("3");
        parameters.add("status");
        parameters.add("scheduled");
    }

    @Test
    public void ChangeFeedback_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.remove(0);
        parameters.remove(1);
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> changeFeedback.execute(parameters));
    }

    @Test
    public void ChangeFeedback_ShouldThrowException_ifChangerInvalid() {
        parameters.set(0, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeFeedback.execute(parameters));
    }

    @Test
    public void ChangeFeedback_ShouldThrowException_ifForeignChanger() {
        parameters.set(0, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeFeedback.execute(parameters));
    }

    @Test
    public void ChangeFeedback_ShouldThrowException_ifTaskInvalid() {
        parameters.set(1, "-2");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeFeedback.execute(parameters));
    }

    @Test
    public void ChangeFeedback_ShouldThrowException_ifTaskWrong() {
        parameters.set(1, "2");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeFeedback.execute(parameters));
    }

    @Test
    public void ChangeFeedback_ShouldThrowException_ifPropertyInvalid() {
        parameters.set(2, "title");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeFeedback.execute(parameters));
    }

    @Test
    public void ChangeFeedback_ShouldChangeStatus_ifNewValueValid() {
        Assertions.assertEquals(
                "User Denis: Successfully changed the STATUS of Feedback with ID 3 to SCHEDULED.",
                changeFeedback.execute(parameters));
        Assertions.assertEquals("Scheduled", repo.findFeedback(3).getStatus().toString());
    }

    @Test
    public void ChangeFeedback_ShouldChangeRating_ifNewValueValid() {
        parameters.set(2, "rating");
        parameters.set(3, "10");
        Assertions.assertEquals(
                "User Denis: Successfully changed the RATING of Feedback with ID 3 to 10.",
                changeFeedback.execute(parameters));
        Assertions.assertEquals(10, repo.findFeedback(3).getRating());
    }
}
