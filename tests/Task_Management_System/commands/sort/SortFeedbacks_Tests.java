package Task_Management_System.commands.sort;

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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortFeedbacks_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command sortFeedbacks;
    List<String> parameters;

    @BeforeEach
    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        sortFeedbacks = cf.createCommand("SortFeedbacks", repo);
        parameters = new ArrayList<>();

        cf.createCommand("CreateTeam", repo).execute(List.of("Team1"));
        cf.createCommand("CreateUser", repo).execute(List.of("Denis"));
        cf.createCommand("CreateUser", repo).execute(List.of("Tisho"));

        cf.createCommand("AddUserToTeam", repo).execute(List.of("Denis", "Team1"));
        cf.createCommand("AddUserToTeam", repo).execute(List.of("Tisho", "Team1"));
        cf.createCommand("CreateBoard", repo).execute(List.of("TaskManagement", "Team1"));

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
    public void SortFeedbacks_ShouldThrowException_ifInvalidNumberOfParameters() {
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> sortFeedbacks.execute(parameters));
    }

    @Test
    public void SortFeedbacks_ShouldThrowException_ifCriterionInvalid() {
        parameters.add("status");
        Assertions.assertThrows(InvalidUserInput.class, () -> sortFeedbacks.execute(parameters));
    }

    @Test
    public void SortFeedbacks_ShouldPrintMessage_ifNoStoriesExist() {
        sortFeedbacks = cf.createCommand("SortFeedbacks", new TaskManagementSystemRepositoryImpl());
        parameters.add("title");
        Assertions.assertEquals("No feedbacks to display.", sortFeedbacks.execute(parameters));
    }

    @Test
    public void SortFeedbacks_ShouldSortByTitle_withValidParameters() {
        parameters.add("title");
        List<String> result = Arrays.stream(sortFeedbacks.execute(parameters)
                        .split(System.lineSeparator()))
                .map(feedback -> feedback.replaceAll(".*Title: ", ""))
                .map(feedback -> feedback.replaceAll(" - Rating.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("000 000 000", result.get(0));
        Assertions.assertEquals("111 111 111", result.get(1));
        Assertions.assertEquals("222 222 222", result.get(2));
        Assertions.assertEquals("jjj jjj jjj", result.get(3));
        Assertions.assertEquals("sss sss sss", result.get(4));
        Assertions.assertEquals("ttt ttt ttt", result.get(5));
    }

    @Test
    public void SortFeedbacks_ShouldSortByRating_withValidParameters() {
        parameters.add("rating");
        List<String> result = Arrays.stream(sortFeedbacks.execute(parameters)
                        .split(System.lineSeparator()))
                .map(feedback -> feedback.replaceAll(".*Rating: ", ""))
                .map(feedback -> feedback.replaceAll(" - Status.*", ""))
                .collect(Collectors.toList());

        Assertions.assertEquals("9", result.get(0));
        Assertions.assertEquals("8", result.get(1));
        Assertions.assertEquals("5", result.get(2));
        Assertions.assertEquals("4", result.get(3));
        Assertions.assertEquals("3", result.get(4));
        Assertions.assertEquals("2", result.get(5));
    }
}