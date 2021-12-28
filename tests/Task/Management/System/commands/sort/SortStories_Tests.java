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

public class SortStories_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command sortStories;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        sortStories = cf.createCommand("SortStories", repo);
        parameters = new ArrayList<>();

        cf.createCommand("CreateTeam", repo).execute(List.of("Team1"));
        cf.createCommand("CreateUser", repo).execute(List.of("Denis"));
        cf.createCommand("CreateUser", repo).execute(List.of("Tisho"));

        cf.createCommand("AddUserToTeam", repo).execute(List.of("Denis", "Team1"));
        cf.createCommand("AddUserToTeam", repo).execute(List.of("Tisho", "Team1"));
        cf.createCommand("CreateBoard", repo).execute(List.of("TaskManagement", "Team1"));

        cf.createCommand("CreateStory", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "zzz zzz zzz", "Test Story Description", "Medium", "Medium", "Tisho"));
        cf.createCommand("CreateStory", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "aaa aaa aaa", "Test Story Description", "High", "Small", "Denis"));
        cf.createCommand("CreateStory", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "xxx xxx xxx", "Test Story Description", "Medium", "Large", "Tisho"));
        cf.createCommand("CreateStory", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "bbb bbb bbb", "Test Story Description", "Low", "Medium", "Denis"));
        cf.createCommand("CreateStory", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "yyy yyy yyy", "Test Story Description", "High", "Large", "Tisho"));
        cf.createCommand("CreateStory", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "ccc ccc ccc", "Test Story Description", "Low", "Small", "Denis"));
    }

    @Test
    public void SortStories_ShouldThrowException_ifInvalidNumberOfParameters() {
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> sortStories.execute(parameters));
    }

    @Test
    public void SortStories_ShouldThrowException_ifCriterionInvalid() {
        parameters.add("assignee");
        Assertions.assertThrows(InvalidUserInput.class, () -> sortStories.execute(parameters));
    }

    @Test
    public void SortStories_ShouldPrintMessage_ifNoStoriesExist() {
        sortStories = cf.createCommand("SortStories", new TaskManagementSystemRepositoryImpl());
        parameters.add("title");
        Assertions.assertEquals("No stories to display.", sortStories.execute(parameters));
    }

    @Test
    public void SortStories_ShouldSortByTitle_withValidParameters() {
        parameters.add("title");
        List<String> result = Arrays.stream(sortStories.execute(parameters)
                        .split(System.lineSeparator()))
                .map(story -> story.replaceAll(".*Title: ", ""))
                .map(story -> story.replaceAll(" - Priority.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("aaa aaa aaa", result.get(0));
        Assertions.assertEquals("bbb bbb bbb", result.get(1));
        Assertions.assertEquals("ccc ccc ccc", result.get(2));
        Assertions.assertEquals("xxx xxx xxx", result.get(3));
        Assertions.assertEquals("yyy yyy yyy", result.get(4));
        Assertions.assertEquals("zzz zzz zzz", result.get(5));
    }

    @Test
    public void SortStories_ShouldSortByPriority_withValidParameters() {
        parameters.add("priority");
        List<String> result = Arrays.stream(sortStories.execute(parameters)
                        .split(System.lineSeparator()))
                .map(story -> story.replaceAll(".*Priority: ", ""))
                .map(story -> story.replaceAll(" - Size.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("High", result.get(0));
        Assertions.assertEquals("High", result.get(1));
        Assertions.assertEquals("Medium", result.get(2));
        Assertions.assertEquals("Medium", result.get(3));
        Assertions.assertEquals("Low", result.get(4));
        Assertions.assertEquals("Low", result.get(5));
    }

    @Test
    public void SortStories_ShouldSortBySize_withValidParameters() {
        parameters.add("size");
        List<String> result = Arrays.stream(sortStories.execute(parameters)
                        .split(System.lineSeparator()))
                .map(story -> story.replaceAll(".*Size: ", ""))
                .map(story -> story.replaceAll(" - Status.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("Large", result.get(0));
        Assertions.assertEquals("Large", result.get(1));
        Assertions.assertEquals("Medium", result.get(2));
        Assertions.assertEquals("Medium", result.get(3));
        Assertions.assertEquals("Small", result.get(4));
        Assertions.assertEquals("Small", result.get(5));
    }
}
