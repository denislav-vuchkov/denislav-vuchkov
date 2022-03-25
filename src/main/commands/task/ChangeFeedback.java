package main.commands.task;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidUserInput;
import main.models.tasks.FeedbackImpl;
import main.models.tasks.contracts.Feedback;
import main.models.tasks.enums.FeedbackStatus;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedback extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeFeedback(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        User changer = getRepository().findUser(parameters.get(0));
        long ID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_ID);
        Feedback feedback = getRepository().findFeedback(ID);
        getRepository().validateUserAndTaskFromSameTeam(changer.getName(), feedback.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        Team team = getRepository().findTeam(feedback);
        changer.log(String.format(TRY, changer.getName(), propertyToChange, "Feedback", ID, newValue), team.getName());

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
        changer.log(event, team.getName());
        return event;
    }
}

