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

public class ChangeBug_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command changeBug;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        changeBug = cf.createCommand("ChangeBug", repo);
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

        cf.createCommand("CreateFeedback", repo).execute(List.of(
                "Tisho",
                "Team1",
                "TaskManagement",
                "test feedback name",
                "test feedback description",
                "5"));

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

        parameters.add("Denis");
        parameters.add("2");
        parameters.add("priority");
        parameters.add("high");
    }

    @Test
    public void ChangeBug_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.remove(0);
        parameters.remove(1);
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> changeBug.execute(parameters));
    }

    @Test
    public void ChangeBug_ShouldThrowException_ifChangerInvalid() {
        parameters.set(0, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeBug.execute(parameters));
    }

    @Test
    public void ChangeBug_ShouldThrowException_ifForeignChanger() {
        parameters.set(0, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeBug.execute(parameters));
    }

    @Test
    public void ChangeBug_ShouldThrowException_ifTaskInvalid() {
        parameters.set(1, "-2");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeBug.execute(parameters));
    }

    @Test
    public void ChangeBug_ShouldThrowException_ifTaskWrong() {
        parameters.set(1, "1");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeBug.execute(parameters));
    }

    @Test
    public void ChangeBug_ShouldThrowException_ifPropertyInvalid() {
        parameters.set(2, "title");
        Assertions.assertThrows(InvalidUserInput.class, () -> changeBug.execute(parameters));
    }

    @Test
    public void ChangeBug_ShouldChangePriority_ifNewValueValid() {
        Assertions.assertEquals(
                "User Denis changed the PRIORITY of Bug with ID 2 to HIGH.",
                changeBug.execute(parameters));
        Assertions.assertEquals("High", repo.findBug(2).getPriority().toString());
    }

    @Test
    public void ChangeBug_ShouldChangeSeverity_ifNewValueValid() {
        parameters.set(2, "severity");
        parameters.set(3, "minor");
        Assertions.assertEquals(
                "User Denis changed the SEVERITY of Bug with ID 2 to MINOR.",
                changeBug.execute(parameters));
        Assertions.assertEquals("Minor", repo.findBug(2).getSeverity().toString());
    }

    @Test
    public void ChangeBug_ShouldChangeStatus_ifNewValueValid() {
        parameters.set(2, "status");
        parameters.set(3, "fixed");
        Assertions.assertEquals(
                "User Denis changed the STATUS of Bug with ID 2 to FIXED.",
                changeBug.execute(parameters));
        Assertions.assertEquals("Fixed", repo.findBug(2).getStatus());
    }
}