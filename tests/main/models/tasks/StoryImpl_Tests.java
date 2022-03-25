package main.models.tasks;

import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Story;
import main.models.tasks.enums.Priority;
import main.models.tasks.enums.Size;
import main.models.tasks.enums.StoryStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoryImpl_Tests {

    Story myStory;

    @BeforeEach
    public void setup() {
        myStory = new StoryImpl(
                0,
                "Not Too Short",
                "Just Right Length",
                Priority.MEDIUM,
                Size.MEDIUM);
    }

    @Test
    public void constructor_initiatesObjectCorrectly_withValidParameters() {
        Assertions.assertEquals(0, myStory.getID());
        Assertions.assertEquals("Not Too Short", myStory.getTitle());
        Assertions.assertEquals("Just Right Length", myStory.getDescription());
        Assertions.assertEquals(Priority.MEDIUM, myStory.getPriority());
        Assertions.assertEquals(Size.MEDIUM, myStory.getSize());
        Assertions.assertEquals(StoryStatus.NOT_DONE, myStory.getStatus());
    }

    @Test
    public void constructor_throwsException_whenTitleIsInvalid() {
        Assertions.assertThrows(InvalidUserInput.class, () -> new StoryImpl(
                10, "Too Short", "Long Enough Description", Priority.LOW, Size.SMALL));
    }

    @Test
    public void constructor_throwsException_whenDescriptionIsInvalid() {
        Assertions.assertThrows(InvalidUserInput.class, () -> new StoryImpl(
                30, "Not Too Short", "Too Short", Priority.MEDIUM, Size.MEDIUM));
    }

    @Test
    public void setSize_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.setSize(Size.MEDIUM));
    }

    @Test
    public void setStatus_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.setStatus(StoryStatus.NOT_DONE));
    }

    @Test
    public void setPriority_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.setPriority(Priority.MEDIUM));
    }

    @Test
    public void addComment_shouldIncreaseSize_whenGivenValidComment() {
        Assertions.assertTrue(myStory.getComments().isEmpty());
        myStory.addComment(new CommentImpl("Test1", "Test1"));
        myStory.addComment(new CommentImpl("Test2", "Test2"));
        myStory.getComments().add(new CommentImpl("Test3", "Test3"));
        myStory.getComments().add(new CommentImpl("Test4", "Test4"));
        Assertions.assertEquals(2, myStory.getComments().size());
    }

    @Test
    public void toString_shouldReturnString_withCorrectFormat() {
        myStory.setStatus(StoryStatus.IN_PROGRESS);
        Assertions.assertEquals("Story ID: 0 - Title: Not Too Short - Priority: Medium - Size: Medium - " +
                "Status: In progress - Assignee: Unassigned - Comments: 0", myStory.toString());
    }

    @Test
    public void printDetails_shouldReturnString_withCorrectFormat() {

        String expectedOutput = String.format("Task type: %s%n" +
                        "ID: %s%n" +
                        "Title: %s%n" +
                        "Description: %s%n" +
                        "Priority: %s%n" +
                        "Size: %s%n" +
                        "Status: %s%n" +
                        "Assignee: %s%n",
                "Story",
                "0",
                "Not Too Short",
                "Just Right Length",
                "Medium",
                "Medium",
                "Not done",
                "Unassigned");

        String actualOutput = myStory.printDetails();
        actualOutput = actualOutput.substring(0, actualOutput.indexOf("Comments"));
        Assertions.assertEquals(expectedOutput.trim(), actualOutput.trim());
    }
}
