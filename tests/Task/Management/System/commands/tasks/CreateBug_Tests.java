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

public class CreateBug_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command createBug;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        createBug = cf.createCommand("CreateBug", repo);
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

        parameters.add("Tisho");
        parameters.add("Team1");
        parameters.add("TaskManagement");
        parameters.add("Activity Log Is Incomplete");
        parameters.add("The activity report needs to show more stuff");
        parameters.add("1. Do this; 2. And this; 3. But don't do that");
        parameters.add("Low");
        parameters.add("Critical");
        parameters.add("Denis");

    }

    @Test
    public void CreateBug_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.remove(0);
        parameters.remove(1);
        parameters.remove(2);
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifCreatorInvalid() {
        parameters.set(0, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifForeignCreator() {
        parameters.set(0, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifTeamInvalid() {
        parameters.set(1, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifForeignTeam() {
        parameters.set(1, "Others");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifBoardInvalid() {
        parameters.set(2, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifForeignBoard() {
        parameters.set(2, "EvilBoard");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifPriorityInvalid() {
        parameters.set(6, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifSeverityInvalid() {
        parameters.set(7, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifAssigneeInvalid() {
        parameters.set(8, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ShouldThrowException_ifForeignAssignee() {
        parameters.set(8, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> createBug.execute(parameters));
    }

    @Test
    public void CreateBug_ReturnsCorrectString_WithValidParameters() {
        Assertions.assertEquals(
                "Bug with ID 1 successfully added to board TaskManagement in team Team1.",
                createBug.execute(parameters));
    }
}