package Task.Management.System.models.tasks;

import Task.Management.System.models.exceptions.InvalidUserInput;
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
                Size.MEDIUM,
                "Mr_No_One");
    }

    @Test
    public void constructor_initiatesObjectCorrectly_withValidParameters() {

        Assertions.assertEquals(0, myStory.getID());
        Assertions.assertEquals("Not Too Short", myStory.getTitle());
        Assertions.assertEquals("Just Right Length", myStory.getDescription());
        Assertions.assertEquals(Priority.MEDIUM, myStory.getPriority());
        Assertions.assertEquals(Size.MEDIUM, myStory.getSize());
        Assertions.assertEquals("Mr_No_One", myStory.getAssignee());
        Assertions.assertEquals(StoryStatus.NOT_DONE.toString(), myStory.getStatus());

        myStory.setTitle("New Valid Title");
        Assertions.assertEquals("New Valid Title", myStory.getTitle());
    }

    @Test
    public void constructor_throwsException_whenTitleIsInvalid() {

        Assertions.assertThrows(InvalidUserInput.class, () -> new StoryImpl(
                10,
                "Too Short",
                "Long Enough Description",
                Priority.LOW,
                Size.SMALL,
                "User10"));

    }

    @Test
    public void constructor_throwsException_whenDescriptionIsInvalid() {

        Assertions.assertThrows(InvalidUserInput.class, () -> new StoryImpl(
                30,
                "Not Too Short",
                "Too Short",
                Priority.MEDIUM,
                Size.MEDIUM,
                "User10"));

    }

    @Test
    public void setName_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.setTitle("Not Too Short"));
    }

    @Test
    public void setDescription_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.setDescription("Just Right Length"));
    }

    @Test
    public void setSize_changesSize_whenUsedCorrectly() {
        myStory.setSize(Size.SMALL);
        Assertions.assertEquals(Size.SMALL, myStory.getSize());
        myStory.setSize(Size.LARGE);
        Assertions.assertEquals(Size.LARGE, myStory.getSize());
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
    public void advanceStatus_changesStatusIfInRange_orElseThrowsException() {
        Assertions.assertEquals(StoryStatus.NOT_DONE.toString(), myStory.getStatus());
        myStory.advanceStatus();
        Assertions.assertEquals(StoryStatus.IN_PROGRESS.toString(), myStory.getStatus());
        myStory.advanceStatus();
        Assertions.assertEquals(StoryStatus.DONE.toString(), myStory.getStatus());
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.advanceStatus());
    }

    @Test
    public void retractStatus_changesStatusIfInRange_orElseThrowsException() {
        myStory.setStatus(StoryStatus.DONE);
        myStory.retractStatus();
        Assertions.assertEquals(StoryStatus.IN_PROGRESS.toString(), myStory.getStatus());
        myStory.retractStatus();
        Assertions.assertEquals(StoryStatus.NOT_DONE.toString(), myStory.getStatus());
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.retractStatus());
    }

    @Test
    public void setPriority_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.setPriority(Priority.MEDIUM));
    }

    @Test
    public void increasePriority_changesSeverityIfInRange_orElseThrowsException() {
        myStory.setPriority(Priority.LOW);
        Assertions.assertEquals(Priority.LOW, myStory.getPriority());
        myStory.increasePriority();
        Assertions.assertEquals(Priority.MEDIUM, myStory.getPriority());
        myStory.increasePriority();
        Assertions.assertEquals(Priority.HIGH, myStory.getPriority());
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.increasePriority());
    }

    @Test
    public void decreasePriority_changesSeverityIfInRange_orElseThrowsException() {
        myStory.setPriority(Priority.HIGH);
        Assertions.assertEquals(Priority.HIGH, myStory.getPriority());
        myStory.decreasePriority();
        Assertions.assertEquals(Priority.MEDIUM, myStory.getPriority());
        myStory.decreasePriority();
        Assertions.assertEquals(Priority.LOW, myStory.getPriority());
        Assertions.assertThrows(InvalidUserInput.class, () -> myStory.decreasePriority());
    }

    @Test
    public void addComment_shouldIncreaseSize_whenGivenValidObject() {
        Assertions.assertTrue(myStory.getComments().isEmpty());
        myStory.addComment(new CommentImpl("Test1", "Test1"));
        myStory.addComment(new CommentImpl("Test2", "Test2"));
        myStory.getComments().add(new CommentImpl("Test3", "Test3"));
        myStory.getComments().add(new CommentImpl("Test4", "Test4"));
        Assertions.assertEquals(2, myStory.getComments().size());
    }
}
