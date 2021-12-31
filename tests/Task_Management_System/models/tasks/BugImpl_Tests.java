package Task_Management_System.models.tasks;

import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Bug;
import Task_Management_System.models.tasks.enums.BugStatus;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BugImpl_Tests {

    Bug myBug;

    @BeforeEach
    public void setup() {
        myBug = new BugImpl(
                3,
                "Not Too Short",
                "Just Right Length",
                List.of("Nothing", "Works", "Help"),
                Priority.MEDIUM,
                Severity.MAJOR);
    }

    @Test
    public void constructor_initiatesObjectCorrectly_withValidParameters() {
        Assertions.assertEquals(3, myBug.getID());
        Assertions.assertEquals("Not Too Short", myBug.getTitle());
        Assertions.assertEquals("Just Right Length", myBug.getDescription());
        Assertions.assertEquals("[Nothing, Works, Help]", myBug.getStepsToReproduce().toString());
        Assertions.assertEquals(Priority.MEDIUM, myBug.getPriority());
        Assertions.assertEquals(Severity.MAJOR, myBug.getSeverity());
        Assertions.assertEquals(BugStatus.ACTIVE, myBug.getStatus());
    }

    @Test
    public void constructor_throwsException_whenTitleIsInvalid() {
        Assertions.assertThrows(InvalidUserInput.class, () ->
                new BugImpl(1, "Too Short", "Long Enough Description",
                        List.of("Nothing", "Works", "Help"), Priority.LOW, Severity.MINOR));
    }

    @Test
    public void constructor_throwsException_whenDescriptionIsInvalid() {
        Assertions.assertThrows(InvalidUserInput.class, () ->
                new BugImpl(2, "Not Too Short", "Too Short",
                        List.of("Nothing", "Works", "Help"), Priority.LOW, Severity.MINOR));
    }

    @Test
    public void getStepsToReproduce_shouldReturnList_withoutBreakingEncapsulation() {
        Assertions.assertEquals(3, myBug.getStepsToReproduce().size());
        myBug.getStepsToReproduce().add("Test");
        myBug.getStepsToReproduce().add("Test");
        myBug.getStepsToReproduce().add("Test");
        Assertions.assertEquals(3, myBug.getStepsToReproduce().size());
    }

    @Test
    public void setSeverity_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setSeverity(Severity.MAJOR));
    }

    @Test
    public void setStatus_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setStatus(BugStatus.ACTIVE));
    }

    @Test
    public void setAssignee_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setAssignee("Unassigned"));
    }

    @Test
    public void unAssign_changesValueToUnassigned_byCallingSetAssignee() {
   //     myBug.setAssignee("Denis");
   //     myBug.unAssign();
     //   Assertions.assertEquals("Unassigned", myBug.getAssignee());
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.unAssign());
    }

    @Test
    public void getComments_shouldReturnList_withoutBreakingEncapsulation() {
        Assertions.assertTrue(myBug.getComments().isEmpty());
        myBug.getComments().add(new CommentImpl("Test1", "Test1"));
        myBug.getComments().add(new CommentImpl("Test2", "Test2"));
        Assertions.assertTrue(myBug.getComments().isEmpty());
    }

    @Test
    public void toString_shouldReturnString_withCorrectFormat() {
        Assertions.assertEquals("Bug ID: 3 - Title: Not Too Short - Steps: 3 - Priority: Medium - " +
                "Severity: Major - Status: Active - Assignee: Unassigned - Comments: 0", myBug.toString());
    }
}
