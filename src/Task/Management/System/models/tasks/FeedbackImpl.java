package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.ValidationHelpers;

import static Task.Management.System.models.contracts.ChangesLogger.*;

public class FeedbackImpl extends TaskBase implements Feedback {

    public static final int RATING_MIN = 0;
    public static final int RATING_MAX = 10;
    public static final String INVALID_RATING_MESSAGE = String.format("Rating cannot be less than %d or more than %d",
            RATING_MIN, RATING_MAX);

    private FeedbackStatus status;
    private int rating;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, Tasks.FEEDBACK, title, description);
        this.status = FeedbackStatus.NEW;
        setRating(rating);
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public void setStatus(FeedbackStatus status) {
        if (!this.status.equals(status)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, status));
            this.status = status;
        } else {
            throw new IllegalArgumentException(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Status", this.status));
        }
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
                throw new IllegalArgumentException("Cannot advance status from fixed.");
        }
    }

    @Override
    public void retractStatus() {
        switch (status) {
            case NEW:
                throw new IllegalArgumentException("Cannot retract status from New.");
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
        ValidationHelpers.validateIntRange(rating, RATING_MIN, RATING_MAX, INVALID_RATING_MESSAGE);

        if (this.rating != rating) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Rating", this.rating, rating));
            this.rating = rating;
        } else {
            throw new IllegalArgumentException(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Rating", this.rating));
        }
    }

    @Override
    public String displayDetails() {
        return String.format("Task type: %s%n" +
                        "%s" +
                        "Rating: %d%n" +
                        "Status: %s%n" +
                        "%s" +
                        "%s",
                this.getClass().getSimpleName().replace("Impl", ""),
                super.displayDetails(),
                getRating(),
                getStatus(),
                displayComments(),
                getHistory());
    }
}
