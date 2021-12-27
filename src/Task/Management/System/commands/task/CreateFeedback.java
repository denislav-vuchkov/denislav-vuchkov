package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedback extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;

    public CreateFeedback(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User creator = getRepository().findUser(parameters.get(0));
        String teamName = parameters.get(1);
        getRepository().validateUserIsFromTeam(creator.getName(), teamName);
        String boardName = parameters.get(2);
        String title = parameters.get(3);
        String description = parameters.get(4);
        int rating = ParsingHelpers.tryParseInt(parameters.get(5), RATING_ERR);
        ValidationHelpers.validateRange(rating, FeedbackImpl.RATING_MIN, FeedbackImpl.RATING_MAX, RATING_ERR);

        creator.log(String.format(USER_CREATED_TASK, creator.getName(), "Feedback", boardName));
        return getRepository().addFeedback(teamName, boardName, title, description, rating);
    }
}
