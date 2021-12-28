package Task.Management.System.commands.sort;

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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortBugs_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command sortBugs;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        sortBugs = cf.createCommand("SortBugs", repo);
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

        cf.createCommand("CreateBug", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "fff fff fff", "Test Bug Description", "One;Two", "Low", "Major", "Tisho"));
        cf.createCommand("CreateBug", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "www www www", "Test Bug Description", "One;Two", "High", "Minor", "Denis"));
        cf.createCommand("CreateBug", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "ddd ddd ddd", "Test Bug Description", "One;Two", "High", "Critical", "Tisho"));
        cf.createCommand("CreateBug", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "vvv vvv vvv", "Test Bug Description", "One;Two", "Medium", "Minor", "Denis"));
        cf.createCommand("CreateBug", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "uuu uuu uuu", "Test Bug Description", "One;Two", "Low", "Major", "Tisho"));
        cf.createCommand("CreateBug", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "eee eee eee", "Test Bug Description", "One;Two", "Medium", "Critical", "Denis"));
    }

    @Test
    public void SortBugs_ShouldThrowException_ifInvalidNumberOfParameters() {
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> sortBugs.execute(parameters));
    }

    @Test
    public void SortBugs_ShouldThrowException_ifCriterionInvalid() {
        parameters.add("assignee");
        Assertions.assertThrows(InvalidUserInput.class, () -> sortBugs.execute(parameters));
    }

    @Test
    public void SortBugs_ShouldPrintMessage_ifNoStoriesExist() {
        sortBugs = cf.createCommand("SortBugs", new TaskManagementSystemRepositoryImpl());
        parameters.add("title");
        Assertions.assertEquals("No bugs to display.", sortBugs.execute(parameters));
    }

    @Test
    public void SortBugs_ShouldSortByTitle_withValidParameters() {
        parameters.add("title");
        List<String> result = Arrays.stream(sortBugs.execute(parameters)
                        .split(System.lineSeparator()))
                .map(bug -> bug.replaceAll(".*Title: ", ""))
                .map(bug -> bug.replaceAll(" - Steps.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("ddd ddd ddd", result.get(0));
        Assertions.assertEquals("eee eee eee", result.get(1));
        Assertions.assertEquals("fff fff fff", result.get(2));
        Assertions.assertEquals("uuu uuu uuu", result.get(3));
        Assertions.assertEquals("vvv vvv vvv", result.get(4));
        Assertions.assertEquals("www www www", result.get(5));
    }

    @Test
    public void SortBugs_ShouldSortByPriority_withValidParameters() {
        parameters.add("priority");
        List<String> result = Arrays.stream(sortBugs.execute(parameters)
                        .split(System.lineSeparator()))
                .map(bug -> bug.replaceAll(".*Priority: ", ""))
                .map(bug -> bug.replaceAll(" - Severity.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("High", result.get(0));
        Assertions.assertEquals("High", result.get(1));
        Assertions.assertEquals("Medium", result.get(2));
        Assertions.assertEquals("Medium", result.get(3));
        Assertions.assertEquals("Low", result.get(4));
        Assertions.assertEquals("Low", result.get(5));
    }

    @Test
    public void SortBugs_ShouldSortBySeverity_withValidParameters() {
        parameters.add("severity");
        List<String> result = Arrays.stream(sortBugs.execute(parameters)
                        .split(System.lineSeparator()))
                .map(bug -> bug.replaceAll(".*Severity: ", ""))
                .map(bug -> bug.replaceAll(" - Status.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("Critical", result.get(0));
        Assertions.assertEquals("Critical", result.get(1));
        Assertions.assertEquals("Major", result.get(2));
        Assertions.assertEquals("Major", result.get(3));
        Assertions.assertEquals("Minor", result.get(4));
        Assertions.assertEquals("Minor", result.get(5));
    }
}
