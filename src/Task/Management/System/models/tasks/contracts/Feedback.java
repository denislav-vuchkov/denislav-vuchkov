package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.FeedbackStatus;

public interface Feedback extends Task {

    int getRating();

    void setRating(int rating);

    void setStatus(FeedbackStatus status);

}
