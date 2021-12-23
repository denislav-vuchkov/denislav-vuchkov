package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedbackCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeFeedbackCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        User user = getRepository().findUser(parameters.get(0).trim());
        int feedbackID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_ID);
        String propertyToChange = parameters.get(2).trim();
        String newValue = parameters.get(3).toUpperCase();

        Feedback feedback = getRepository().findFeedback(feedbackID);

        switch (propertyToChange.toUpperCase()) {
            case "Rating":
                int rating = ParsingHelpers.tryParseInt(newValue, FEEDBACK_RATING_ERROR);
                ValidationHelpers.validateIntRange(
                        rating, FeedbackImpl.RATING_MIN, FeedbackImpl.RATING_MAX, FEEDBACK_RATING_ERROR);
                feedback.setRating(rating);
                break;
            case "STATUS":
                FeedbackStatus status = ParsingHelpers.tryParseEnum(newValue, FeedbackStatus.class);
                feedback.setStatus(status);
                break;
            default:
                throw new InvalidUserInput(INVALID_PROPERTY);
        }

        user.recordActivity(
                String.format(RECORD_ACTIVITY, user.getName(), propertyToChange, "Feedback", feedbackID, newValue));

        return String.format(PROPERTY_UPDATED, propertyToChange, "Feedback", feedbackID, newValue);
    }
}

