package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    public CreateFeedbackCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        String boardName = parameters.get(1);
        String title = parameters.get(2);
        String description = parameters.get(3);
        int rating = ParsingHelpers.tryParseInt(parameters.get(4), FEEDBACK_RATING_ERROR);
        ValidationHelpers.validateIntRange(
                rating, FeedbackImpl.RATING_MIN, FeedbackImpl.RATING_MAX, FEEDBACK_RATING_ERROR);

        return getRepository().addFeedback(teamName, boardName, title, description, rating);
    }
}
