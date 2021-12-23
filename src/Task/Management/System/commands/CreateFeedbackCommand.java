package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;

    public CreateFeedbackCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0));
        String teamName = parameters.get(1);
        getRepository().validateUserIsFromTeam(user.getName(), teamName);
        String boardName = parameters.get(2);
        String title = parameters.get(3);
        String description = parameters.get(4);
        int rating = ParsingHelpers.tryParseInt(parameters.get(5), FEEDBACK_RATING_ERROR);
        ValidationHelpers.validateIntRange(
                rating, FeedbackImpl.RATING_MIN, FeedbackImpl.RATING_MAX, FEEDBACK_RATING_ERROR);

        user.recordActivity(String.format(USER_CREATED_TASK, user.getName(), "Feedback", boardName));

        return getRepository().addFeedback(teamName, boardName, title, description, rating);
    }
}
