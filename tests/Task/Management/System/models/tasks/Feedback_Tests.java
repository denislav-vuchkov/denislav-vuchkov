package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Feedback_Tests {

    Feedback myFeedback;

    @BeforeEach
    public void setup() {

        myFeedback = new FeedbackImpl(
                123456,
                "Not Too Short",
                "Just Right Length",
                5);
    }

    @Test
    public void constructor_initiatesObjectCorrectly_withValidParameters() {

        Assertions.assertEquals(123456, myFeedback.getID());
        Assertions.assertEquals("Not Too Short", myFeedback.getTitle());
        Assertions.assertEquals("Just Right Length", myFeedback.getDescription());
        Assertions.assertEquals(5, myFeedback.getRating());
        Assertions.assertEquals(FeedbackStatus.NEW.toString(), myFeedback.getStatus());

        myFeedback.setRating(6);
        Assertions.assertEquals(6, myFeedback.getRating());
    }

    @Test
    public void constructor_throwsException_whenTitleIsInvalid() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(
                10,
                "Too Short",
                "Long Enough Description",
                0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(
                20,
                "!".repeat(100),
                "Long Enough Description",
                10));
    }

    @Test
    public void constructor_throwsException_whenDescriptionIsInvalid() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(
                1000,
                "Not Too Short",
                "Too Short",
                1));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(
                2000,
                "Not Too Short",
                "!".repeat(1000),
                9));
    }

    @Test
    public void constructor_throwsException_whenRatingIsInvalid() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(
                111,
                "Not Too Short",
                "Just Right Length",
                -5));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(
                222,
                "Not Too Short",
                "Just Right Length",
                50));
    }

    @Test
    public void setName_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> myFeedback.setTitle("Not Too Short"));
    }

    @Test
    public void setDescription_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> myFeedback.setDescription("Just Right Length"));
    }

    @Test
    public void setRating_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> myFeedback.setRating(5));
    }

    @Test
    public void setStatus_throwsException_whenNewOneIsTheSameAsOld() {
        myFeedback.setStatus(FeedbackStatus.SCHEDULED);
        Assertions.assertThrows(IllegalArgumentException.class, () -> myFeedback.setStatus(FeedbackStatus.SCHEDULED));
    }

    @Test
    public void advanceStatus_changesStatusIfInRange_orElseThrowsException() {
        Assertions.assertEquals(FeedbackStatus.NEW.toString(), myFeedback.getStatus());
        myFeedback.advanceStatus();
        Assertions.assertEquals(FeedbackStatus.UNSCHEDULED.toString(), myFeedback.getStatus());
        myFeedback.advanceStatus();
        Assertions.assertEquals(FeedbackStatus.SCHEDULED.toString(), myFeedback.getStatus());
        myFeedback.advanceStatus();
        Assertions.assertEquals(FeedbackStatus.DONE.toString(), myFeedback.getStatus());
        Assertions.assertThrows(IllegalArgumentException.class, () -> myFeedback.advanceStatus());
    }

    @Test
    public void retractStatus_changesStatusIfInRange_orElseThrowsException() {
        myFeedback.setStatus(FeedbackStatus.DONE);
        myFeedback.retractStatus();
        Assertions.assertEquals(FeedbackStatus.SCHEDULED.toString(), myFeedback.getStatus());
        myFeedback.retractStatus();
        Assertions.assertEquals(FeedbackStatus.UNSCHEDULED.toString(), myFeedback.getStatus());
        myFeedback.retractStatus();
        Assertions.assertEquals(FeedbackStatus.NEW.toString(), myFeedback.getStatus());
        Assertions.assertThrows(IllegalArgumentException.class, () -> myFeedback.retractStatus());
    }
}
