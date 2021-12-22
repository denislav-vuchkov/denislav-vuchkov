package Task.Management.System.models.tasks;

import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
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
                Severity.MAJOR,
                "User10");
    }

    @Test
    public void constructor_initiatesObjectCorrectly_withValidParameters() {

        Assertions.assertEquals(3, myBug.getID());
        Assertions.assertEquals("Not Too Short", myBug.getTitle());
        Assertions.assertEquals("Just Right Length", myBug.getDescription());
        Assertions.assertEquals(Priority.MEDIUM, myBug.getPriority());
        Assertions.assertEquals(Severity.MAJOR, myBug.getSeverity());
        Assertions.assertEquals("User10", myBug.getAssignee());
        Assertions.assertEquals(BugStatus.ACTIVE.toString(), myBug.getStatus());

        String stepsOutput = "--STEPS TO REPRODUCE--\n" +
                "1. Nothing\n" +
                "2. Works\n" +
                "3. Help\n" +
                "--STEPS TO REPRODUCE--\n";
        Assertions.assertEquals(stepsOutput, myBug.getStepsToReproduce());

        myBug.setDescription("This Is The New Valid Description");
        Assertions.assertEquals("This Is The New Valid Description", myBug.getDescription());

        myBug = new BugImpl(
                99,
                "Not Too Short",
                "Just Right Length",
                List.of("A", "B", "C"),
                Priority.HIGH,
                Severity.CRITICAL);

        Assertions.assertEquals(99, myBug.getID());
        Assertions.assertEquals("Not Too Short", myBug.getTitle());
        Assertions.assertEquals("Just Right Length", myBug.getDescription());
        Assertions.assertEquals(Priority.HIGH, myBug.getPriority());
        Assertions.assertEquals(Severity.CRITICAL, myBug.getSeverity());
        Assertions.assertEquals("Unassigned", myBug.getAssignee());
        Assertions.assertEquals(BugStatus.ACTIVE.toString(), myBug.getStatus());

        stepsOutput = "--STEPS TO REPRODUCE--\n" +
                "1. A\n" +
                "2. B\n" +
                "3. C\n" +
                "--STEPS TO REPRODUCE--\n";

        Assertions.assertEquals(stepsOutput, myBug.getStepsToReproduce());
    }

    @Test
    public void constructor_throwsException_whenTitleIsInvalid() {

        Assertions.assertThrows(InvalidUserInput.class, () -> new BugImpl(
                1,
                "Too Short",
                "Long Enough Description",
                List.of("Nothing", "Works", "Help"),
                Priority.LOW,
                Severity.MINOR,
                "User10"));

        Assertions.assertThrows(InvalidUserInput.class, () -> new BugImpl(
                1,
                "!".repeat(100),
                "Long Enough Description",
                List.of("Nothing", "Works", "Help"),
                Priority.LOW,
                Severity.MINOR));
    }

    @Test
    public void constructor_throwsException_whenDescriptionIsInvalid() {

        Assertions.assertThrows(InvalidUserInput.class, () -> new BugImpl(
                2,
                "Not Too Short",
                "Too Short",
                List.of("Nothing", "Works", "Help"),
                Priority.LOW,
                Severity.MINOR,
                "User10"));

        Assertions.assertThrows(InvalidUserInput.class, () -> new BugImpl(
                2,
                "Not Too Short",
                "!".repeat(1000),
                List.of("Nothing", "Works", "Help"),
                Priority.LOW,
                Severity.MINOR));
    }

    @Test
    public void setName_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setTitle("Not Too Short"));
    }

    @Test
    public void setDescription_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setDescription("Just Right Length"));
    }

    @Test
    public void setSeverity_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setSeverity(Severity.MAJOR));
    }

    @Test
    public void increaseSeverity_changesSeverityIfInRange_orElseThrowsException() {
        myBug.setSeverity(Severity.MINOR);
        Assertions.assertEquals(Severity.MINOR, myBug.getSeverity());
        myBug.increaseSeverity();
        Assertions.assertEquals(Severity.MAJOR, myBug.getSeverity());
        myBug.increaseSeverity();
        Assertions.assertEquals(Severity.CRITICAL, myBug.getSeverity());
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.increaseSeverity());
    }

    @Test
    public void decreaseSeverity_changesSeverityIfInRange_orElseThrowsException() {
        myBug.setSeverity(Severity.CRITICAL);
        Assertions.assertEquals(Severity.CRITICAL, myBug.getSeverity());
        myBug.decreaseSeverity();
        Assertions.assertEquals(Severity.MAJOR, myBug.getSeverity());
        myBug.decreaseSeverity();
        Assertions.assertEquals(Severity.MINOR, myBug.getSeverity());
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.decreaseSeverity());
    }

    @Test
    public void setStatus_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setStatus(BugStatus.ACTIVE));
    }

    @Test
    public void advanceStatus_changesStatusIfInRange_orElseThrowsException() {
        myBug.advanceStatus();
        Assertions.assertEquals(BugStatus.FIXED.toString(), myBug.getStatus());
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.advanceStatus());
    }

    @Test
    public void retractStatus_changesStatusIfInRange_orElseThrowsException() {
        myBug.setStatus(BugStatus.FIXED);
        myBug.retractStatus();
        Assertions.assertEquals(BugStatus.ACTIVE.toString(), myBug.getStatus());
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.retractStatus());
    }

    @Test
    public void setAssignee_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.setAssignee("User10"));
    }

    @Test
    public void unAssign_changesValueToUnassigned_byCallingSetAssignee() {
        Assertions.assertEquals("User10", myBug.getAssignee());
        myBug.unAssign();
        Assertions.assertEquals("Unassigned", myBug.getAssignee());
        Assertions.assertThrows(InvalidUserInput.class, () -> myBug.unAssign());
    }

    @Test
    public void getComments_shouldReturnList_withoutBreakingEncapsulation() {
        Assertions.assertTrue(myBug.getComments().isEmpty());
        myBug.getComments().add(new CommentImpl("Test1", "Test1"));
        myBug.getComments().add(new CommentImpl("Test2", "Test2"));
        Assertions.assertTrue(myBug.getComments().isEmpty());
    }
}
