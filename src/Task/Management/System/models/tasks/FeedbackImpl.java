package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.ValidationHelpers;

import static Task.Management.System.models.contracts.EventLogger.CHANGE_MESSAGE;
import static Task.Management.System.models.contracts.EventLogger.IMPOSSIBLE_CHANGE_MESSAGE;

public class FeedbackImpl extends TaskBase implements Feedback {

    public static final int RATING_UNINITIALIZED = -1;
    public static final int RATING_MIN = 0;
    public static final int RATING_MAX = 10;
    public static final String INVALID_RATING_MESSAGE = String.format("Rating cannot be less than %d or more than %d",
            RATING_MIN, RATING_MAX);

    public static final String LOWER_BOUNDARY = "Cannot decrease %s further than %s.";
    public static final String UPPER_BOUNDARY = "Cannot increase %s beyond %s.";

    private FeedbackStatus status;
    private int rating = RATING_UNINITIALIZED;

    public FeedbackImpl(long id, String title, String description, int rating) {
        super(id, Tasks.FEEDBACK, title, description);
        setRating(rating);
        setStatus(FeedbackStatus.NEW);
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public void setStatus(FeedbackStatus status) {
        if (this.status == null) {
            this.status = status;
            return;
        }
        if (this.status.equals(status)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Status", this.status));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, status));
        this.status = status;
    }

    @Override
    public void advanceStatus() {
        switch (status) {
            case NEW:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, FeedbackStatus.UNSCHEDULED));
                this.status = FeedbackStatus.UNSCHEDULED;
                break;
            case UNSCHEDULED:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, FeedbackStatus.SCHEDULED));
                this.status = FeedbackStatus.SCHEDULED;
                break;
            case SCHEDULED:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, FeedbackStatus.DONE));
                this.status = FeedbackStatus.DONE;
                break;
            case DONE:
                throw new InvalidUserInput(String.format(UPPER_BOUNDARY, "status", FeedbackStatus.DONE));
        }
    }

    @Override
    public void retractStatus() {
        switch (status) {
            case NEW:
                throw new InvalidUserInput(String.format(LOWER_BOUNDARY, "status", FeedbackStatus.NEW));
            case UNSCHEDULED:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, FeedbackStatus.NEW));
                this.status = FeedbackStatus.NEW;
                break;
            case SCHEDULED:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, FeedbackStatus.UNSCHEDULED));
                this.status = FeedbackStatus.UNSCHEDULED;
                break;
            case DONE:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, FeedbackStatus.SCHEDULED));
                this.status = FeedbackStatus.SCHEDULED;
                break;
        }
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
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Rating", this.rating));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Rating", this.rating, rating));
        this.rating = rating;
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
                printComments(),
                getLog());
    }

    @Override
    public String toString() {
        return String.format("Task type: %s - ID: %d - Title: %s - Rating: %s - Status: %s",
                this.getClass().getSimpleName().replace("Impl", ""),
                getID(), getTitle(), getRating(), getStatus());
    }
}
