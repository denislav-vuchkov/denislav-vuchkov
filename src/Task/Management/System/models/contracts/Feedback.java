package Task.Management.System.models.contracts;

import Task.Management.System.models.enums.FeedbackStatus;

public interface Feedback extends Task {

    int getRating();

    void setRating(int rating);

    void setStatus(FeedbackStatus status);

}
