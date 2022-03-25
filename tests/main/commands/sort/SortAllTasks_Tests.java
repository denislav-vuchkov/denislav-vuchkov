package main.commands.sort;

import main.commands.contracts.Command;
import main.core.CommandFactoryImpl;
import main.core.TaskManagementSystemRepositoryImpl;
import main.core.contracts.CommandFactory;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidNumberOfArguments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortAllTasks_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command sortAllTasks;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        sortAllTasks = cf.createCommand("SortAllTasks", repo);
        parameters = new ArrayList<>();

        cf.createCommand("CreateTeam", repo).execute(List.of("Team1"));
        cf.createCommand("CreateUser", repo).execute(List.of("Denis"));
        cf.createCommand("CreateUser", repo).execute(List.of("Tisho"));

        cf.createCommand("AddUserToTeam", repo).execute(List.of("Denis", "Team1"));
        cf.createCommand("AddUserToTeam", repo).execute(List.of("Tisho", "Team1"));
        cf.createCommand("CreateBoard", repo).execute(List.of("TaskManagement", "Team1"));

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

        cf.createCommand("CreateFeedback", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "ttt ttt ttt", "Test Feedback Description", "9"));
        cf.createCommand("CreateFeedback", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "111 111 111", "Test Feedback Description", "2"));
        cf.createCommand("CreateFeedback", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "jjj jjj jjj", "Test Feedback Description", "5"));
        cf.createCommand("CreateFeedback", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "222 222 222", "Test Feedback Description", "8"));
        cf.createCommand("CreateFeedback", repo).execute(List.of("Denis", "Team1", "TaskManagement",
                "sss sss sss", "Test Feedback Description", "3"));
        cf.createCommand("CreateFeedback", repo).execute(List.of("Tisho", "Team1", "TaskManagement",
                "000 000 000", "Test Feedback Description", "4"));
    }

    @Test
    public void SortAllTasks_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.add("title");
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> sortAllTasks.execute(parameters));
    }

    @Test
    public void SortAllTasks_ShouldPrintMessage_ifNoStoriesExist() {
        sortAllTasks = cf.createCommand("SortAllTasks", new TaskManagementSystemRepositoryImpl());
        Assertions.assertEquals("No tasks to display.", sortAllTasks.execute(parameters));
    }

    @Test
    public void SortAllTasks_ShouldSortByTitle_regardlessOfTaskType() {
        List<String> result = Arrays.stream(sortAllTasks.execute(parameters)
                        .split(System.lineSeparator()))
                .map(task -> task.replaceAll(".*Title: ", ""))
                .map(bug -> bug.replaceAll(" - Steps.*", ""))
                .map(story -> story.replaceAll(" - Priority.*", ""))
                .map(feedback -> feedback.replaceAll(" - Rating.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("000 000 000", result.get(0));
        Assertions.assertEquals("111 111 111", result.get(1));
        Assertions.assertEquals("222 222 222", result.get(2));
        Assertions.assertEquals("aaa aaa aaa", result.get(3));
        Assertions.assertEquals("bbb bbb bbb", result.get(4));
        Assertions.assertEquals("ccc ccc ccc", result.get(5));
        Assertions.assertEquals("ddd ddd ddd", result.get(6));
        Assertions.assertEquals("eee eee eee", result.get(7));
        Assertions.assertEquals("fff fff fff", result.get(8));
        Assertions.assertEquals("jjj jjj jjj", result.get(9));
        Assertions.assertEquals("sss sss sss", result.get(10));
        Assertions.assertEquals("ttt ttt ttt", result.get(11));
        Assertions.assertEquals("uuu uuu uuu", result.get(12));
        Assertions.assertEquals("vvv vvv vvv", result.get(13));
        Assertions.assertEquals("www www www", result.get(14));
        Assertions.assertEquals("xxx xxx xxx", result.get(15));
        Assertions.assertEquals("yyy yyy yyy", result.get(16));
        Assertions.assertEquals("zzz zzz zzz", result.get(17));
    }
}
