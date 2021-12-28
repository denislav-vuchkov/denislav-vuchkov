package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
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
        Assertions.assertEquals(StoryStatus.NOT_DONE.toString(), myStory.getStatus());
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
        Assertions.assertEquals("Story ID: 0 - Title: Not Too Short - Priority: Medium - Size: Medium - " +
                "Status: Not done - Assignee: Unassigned - Comments: 0", myStory.toString());
    }
}
