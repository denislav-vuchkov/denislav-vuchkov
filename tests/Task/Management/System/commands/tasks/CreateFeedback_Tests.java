package Task.Management.System.commands.tasks;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidNumberOfArguments;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateFeedback_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command createFeedback;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        createFeedback = cf.createCommand("CreateFeedback", repo);
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
        parameters.add("Everything's fine");
        parameters.add("But deep down I am disappointed of it all.");
        parameters.add("7");
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.remove(0);
        parameters.remove(1);
        parameters.remove(2);
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifCreatorInvalid() {
        parameters.set(0, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifForeignCreator() {
        parameters.set(0, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifTeamInvalid() {
        parameters.set(1, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifForeignTeam() {
        parameters.set(1, "Others");
        Assertions.assertThrows(InvalidUserInput.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifBoardInvalid() {
        parameters.set(2, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifForeignBoard() {
        parameters.set(2, "EvilBoard");
        Assertions.assertThrows(InvalidUserInput.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateFeedback_ShouldThrowException_ifRatingInvalid() {
        parameters.set(5, "-7");
        Assertions.assertThrows(InvalidUserInput.class, () -> createFeedback.execute(parameters));
    }

    @Test
    public void CreateStory_ReturnsCorrectString_WithValidParameters() {
        Assertions.assertEquals(0, repo.getFeedbacks().size());
        Assertions.assertEquals(
                "Feedback with ID 1 successfully added to board TaskManagement in team Team1.",
                createFeedback.execute(parameters));
        Assertions.assertEquals(1, repo.getFeedbacks().size());
        Feedback feedback = repo.findFeedback(1);
        Assertions.assertEquals("Everything's fine", feedback.getTitle());
        Assertions.assertEquals(7, feedback.getRating());
    }
}
