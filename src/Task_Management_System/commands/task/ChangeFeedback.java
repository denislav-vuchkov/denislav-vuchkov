package Task_Management_System.commands.task;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.FeedbackImpl;
import Task_Management_System.models.tasks.contracts.Feedback;
import Task_Management_System.models.tasks.enums.FeedbackStatus;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedback extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeFeedback(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User changer = getRepository().findByName(getRepository().getUsers(), parameters.get(0), USER);
        int ID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_ID);
        Feedback feedback = getRepository().findFeedback(ID);
        getRepository().validateUserAndTaskFromSameTeam(changer.getName(), feedback.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        changer.log(String.format(ATTEMPTED_CHANGE, changer.getName(), propertyToChange, "Feedback", ID, newValue));

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

        String event = String.format(SUCCESSFUL_CHANGE, changer.getName(), propertyToChange, "Feedback", ID, newValue);
        changer.log(event);
        return event;
    }
}

