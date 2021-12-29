package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.FormatHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.stream.Collectors;

import static Task.Management.System.models.logger.contracts.Logger.TASK_CHANGE;

public class FeedbackImpl extends TaskBase implements Feedback {

    public static final int RATING_UNINITIALIZED = -1;
    public static final int RATING_MIN = 0;
    public static final int RATING_MAX = 10;
    public static final String INVALID_RATING_MESSAGE = String.format("Rating cannot be less than %d or more than %d",
            RATING_MIN, RATING_MAX);
    public static final String RATING_FIELD = "Rating";

    private int rating = RATING_UNINITIALIZED;

    public FeedbackImpl(long id, String title, String description, int rating) {
        super(id, Tasks.FEEDBACK, title, description, FeedbackStatus.NEW);
        setRating(rating);
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        ValidationHelpers.validateRange(rating, RATING_MIN, RATING_MAX, INVALID_RATING_MESSAGE);
        if (this.rating == -1) {
            this.rating = rating;
            return;
        }
        checkForDuplication(getRating(), rating, RATING_FIELD);
        logActivity(String.format(TASK_CHANGE, Tasks.FEEDBACK, getID(), RATING_FIELD, this.rating, rating));
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                FormatHelpers.getType(this), getID(), getTitle(), getRating(), getStatus(), getComments().size());
    }

    @Override
    public String printDetails() {
        return String.format("Task type: %s%n" +
                        "%s" +
                        "Rating: %d%n" +
                        "Status: %s%n" +
                        "%s" +
                        "%s",
                FormatHelpers.getType(this),
                super.printDetails(),
                getRating(),
                getStatus(),
                getComments().stream().map(Comment::toString).collect(Collectors.joining(System.lineSeparator())),
                getLog());
    }
}
