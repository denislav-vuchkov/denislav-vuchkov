package main.models.tasks;

import main.models.logger.EventImpl;
import main.models.tasks.contracts.Feedback;
import main.models.tasks.enums.FeedbackStatus;
import main.models.tasks.enums.Tasks;
import main.utils.FormatHelpers;
import main.utils.ValidationHelpers;

import java.util.stream.Collectors;

public class FeedbackImpl extends TaskBase implements Feedback {

    public static final int RATING_MIN = 0;
    public static final int RATING_MAX = 10;
    public static final String INVALID_RATING_MESSAGE = String.format(
            "Rating cannot be less than %d or more than %d", RATING_MIN, RATING_MAX);
    public static final String RATING_FIELD = "Rating";

    private Integer rating;

    public FeedbackImpl(long id, String title, String description, int rating) {
        super(id, Tasks.FEEDBACK, title, description, FeedbackStatus.NEW);
        setRating(rating);
    }

    @Override
    public Integer getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        ValidationHelpers.validateRange(rating, RATING_MIN, RATING_MAX, INVALID_RATING_MESSAGE);
        checkForDuplication(getRating(), rating, RATING_FIELD);
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
                        "%s%n" +
                        "%s",
                FormatHelpers.getType(this),
                super.printDetails(),
                getRating(),
                getStatus(),
                printComments(),
                CHANGES_HISTORY +
                        getLog().stream().map(EventImpl::toString).collect(Collectors.joining(System.lineSeparator())));
    }
}
