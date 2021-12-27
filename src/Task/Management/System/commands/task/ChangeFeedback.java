package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedback extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeFeedback(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User changer = getRepository().findUser(parameters.get(0).trim());
        int ID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_ID);
        Feedback feedback = getRepository().findFeedback(ID);
        getRepository().validateUserAndTaskFromSameTeam(changer.getName(), feedback.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        switch (propertyToChange) {
            case "RATING":
                int rating = ParsingHelpers.tryParseInt(newValue, RATING_ERR);
                ValidationHelpers.validateRange(rating, FeedbackImpl.RATING_MIN, FeedbackImpl.RATING_MAX, RATING_ERR);
                feedback.setRating(rating);
                break;
            case "STATUS":
                FeedbackStatus status = ParsingHelpers.tryParseEnum(newValue, FeedbackStatus.class);
                feedback.setStatus(status);
                break;
            default:
                throw new InvalidUserInput(INVALID_PROPERTY);
        }

        String result = String.format(RECORD_ACTIVITY, changer.getName(), propertyToChange, "Feedback", ID, newValue);
        changer.log(result);
        return result;
    }
}

