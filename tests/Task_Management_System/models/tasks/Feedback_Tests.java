package Task_Management_System.models.tasks;

import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Feedback;
import Task_Management_System.models.tasks.enums.FeedbackStatus;
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
        Assertions.assertEquals(FeedbackStatus.NEW, myFeedback.getStatus());
    }

    @Test
    public void constructor_throwsException_whenTitleIsInvalid() {
        Assertions.assertThrows(InvalidUserInput.class, () -> new FeedbackImpl(
                10, "Too Short", "Long Enough Description", 0));
        Assertions.assertThrows(InvalidUserInput.class, () -> new FeedbackImpl(
                20, "!".repeat(100), "Long Enough Description", 10));
    }

    @Test
    public void constructor_throwsException_whenDescriptionIsInvalid() {
        Assertions.assertThrows(InvalidUserInput.class, () -> new FeedbackImpl(
                1000, "Not Too Short", "Too Short", 1));
        Assertions.assertThrows(InvalidUserInput.class, () -> new FeedbackImpl(
                2000, "Not Too Short", "!".repeat(1000), 9));
    }

    @Test
    public void constructor_throwsException_whenRatingIsInvalid() {
        Assertions.assertThrows(InvalidUserInput.class, () -> new FeedbackImpl(
                111, "Not Too Short", "Just Right Length", -5));
        Assertions.assertThrows(InvalidUserInput.class, () -> new FeedbackImpl(
                222, "Not Too Short", "Just Right Length", 50));
    }

    @Test
    public void setRating_throwsException_whenNewOneIsTheSameAsOld() {
        Assertions.assertThrows(InvalidUserInput.class, () -> myFeedback.setRating(5));
    }

    @Test
    public void setStatus_throwsException_whenNewOneIsTheSameAsOld() {
        myFeedback.setStatus(FeedbackStatus.SCHEDULED);
        Assertions.assertThrows(InvalidUserInput.class, () -> myFeedback.setStatus(FeedbackStatus.SCHEDULED));
    }

    @Test
    public void toString_shouldReturnString_withCorrectFormat() {
        Assertions.assertEquals("Feedback ID: 123456 - Title: Not Too Short - Rating: 5 - " +
                "Status: New - Comments: 0", myFeedback.toString());
    }
}
