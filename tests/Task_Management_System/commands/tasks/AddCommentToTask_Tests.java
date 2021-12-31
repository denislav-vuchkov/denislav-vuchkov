package Task_Management_System.commands.tasks;

import Task_Management_System.commands.contracts.Command;
import Task_Management_System.core.CommandFactoryImpl;
import Task_Management_System.core.TaskManagementSystemRepositoryImpl;
import Task_Management_System.core.contracts.CommandFactory;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidNumberOfArguments;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Bug;
import Task_Management_System.models.tasks.contracts.Feedback;
import Task_Management_System.models.tasks.contracts.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AddCommentToTask_Tests {

    CommandFactory cf;
    TaskManagementSystemRepository repo;
    Command addComment;
    List<String> parameters;

    @BeforeEach

    public void initiatePreliminaryProgramState() {

        cf = new CommandFactoryImpl();
        repo = new TaskManagementSystemRepositoryImpl();
        addComment = cf.createCommand("AddCommentToTask", repo);
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

        cf.createCommand("CreateFeedback", repo).execute(List.of(
                "Outsider",
                "Others",
                "EvilBoard",
                "test feedback name",
                "test feedback description",
                "0"));

        parameters.add("Denis");
        parameters.add("1");
        parameters.add("This bug is driving me nuts.");
    }

    @Test
    public void AddCommentToTask_ShouldThrowException_ifInvalidNumberOfParameters() {
        parameters.remove(0);
        parameters.remove(1);
        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> addComment.execute(parameters));
    }

    @Test
    public void AddCommentToTask_ShouldThrowException_ifAuthorInvalid() {
        parameters.set(0, "Invalid");
        Assertions.assertThrows(InvalidUserInput.class, () -> addComment.execute(parameters));
    }

    @Test
    public void AddCommentToTask_ShouldThrowException_ifForeignAuthor() {
        parameters.set(0, "Outsider");
        Assertions.assertThrows(InvalidUserInput.class, () -> addComment.execute(parameters));
    }

    @Test
    public void AddCommentToTask_ShouldThrowException_ifTaskInvalid() {
        parameters.set(1, "-2");
        Assertions.assertThrows(InvalidUserInput.class, () -> addComment.execute(parameters));
    }

    @Test
    public void AddCommentToTask_ShouldThrowException_ifForeignTask() {
        parameters.set(1, "4");
        Assertions.assertThrows(InvalidUserInput.class, () -> addComment.execute(parameters));
    }

    @Test
    public void AddCommentToTask_createsNewBugComment_ifAllParametersCorrect() {
        parameters.set(0, "Tisho");
        parameters.set(1, "1");
        Bug bug = repo.findBug(1);
        Assertions.assertEquals(0, bug.getComments().size());
        Assertions.assertEquals("User Tisho: Added a comment to Bug with ID 1.", addComment.execute(parameters));
        Assertions.assertEquals(1, bug.getComments().size());
        Assertions.assertEquals("This bug is driving me nuts.", bug.getComments().get(0).getContent());
    }

    @Test
    public void AddCommentToTask_createsNewStoryComment_ifAllParametersCorrect() {
        parameters.set(1, "2");
        parameters.set(2, "This story is very interesting.");
        Story story = repo.findStory(2);
        Assertions.assertEquals(0, story.getComments().size());
        Assertions.assertEquals("User Denis: Added a comment to Story with ID 2.", addComment.execute(parameters));
        Assertions.assertEquals(1, story.getComments().size());
        Assertions.assertEquals("This story is very interesting.", story.getComments().get(0).getContent());
    }

    @Test
    public void AddCommentToTask_createsNewFeedbackComment_ifAllParametersCorrect() {
        parameters.set(1, "3");
        parameters.set(2, "I also wrote something.");
        Feedback feedback = repo.findFeedback(3);
        Assertions.assertEquals(0, feedback.getComments().size());
        Assertions.assertEquals("User Denis: Added a comment to Feedback with ID 3.", addComment.execute(parameters));
        Assertions.assertEquals("User Denis: Added a comment to Feedback with ID 3.", addComment.execute(parameters));
        Assertions.assertEquals(2, feedback.getComments().size());
        Assertions.assertEquals("I also wrote something.", feedback.getComments().get(1).getContent());
    }
}
