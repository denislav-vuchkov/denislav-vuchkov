package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.ValidationHelpers;

import java.util.stream.Collectors;

import static Task.Management.System.models.contracts.EventLogger.CHANGE;
import static Task.Management.System.models.contracts.EventLogger.DUPLICATE;

public class FeedbackImpl extends TaskBase implements Feedback {

    public static final int RATING_UNINITIALIZED = -1;
    public static final int RATING_MIN = 0;
    public static final int RATING_MAX = 10;
    public static final String INVALID_RATING_MESSAGE = String.format("Rating cannot be less than %d or more than %d",
            RATING_MIN, RATING_MAX);

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
        if (this.rating == rating) {
            throw new InvalidUserInput(String.format(DUPLICATE, "Rating", this.rating));
        }
        addChangeToHistory(String.format(CHANGE, this.getClass().getSimpleName().replace("Impl", ""),
                getID(), "Rating", this.rating, rating));
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                this.getClass().getSimpleName().replace("Impl", ""),
                getID(), getTitle(), getRating(), getStatus(), getComments().size());
    }

    @Override
    public String printDetails() {
        return String.format("Task type: %s%n" +
                        "%s" +
                        "Rating: %d%n" +
                        "Status: %s%n" +
                        "%s" +
                        "%s",
                this.getClass().getSimpleName().replace("Impl", ""),
                super.printDetails(),
                getRating(),
                getStatus(),
                getComments().stream().map(Comment::toString).collect(Collectors.joining(System.lineSeparator())),
                getLog());
    }
}
