package Task_Management_System.commands.task;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.tasks.FeedbackImpl;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedback extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;

    public CreateFeedback(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User creator = getRepository().findByName(getRepository().getUsers(), parameters.get(0), USER);
        String teamName = parameters.get(1);
        getRepository().validateUserIsFromTeam(creator.getName(), teamName);
        String boardName = parameters.get(2);
        String title = parameters.get(3);
        String description = parameters.get(4);
        int rating = ParsingHelpers.tryParseInt(parameters.get(5), RATING_ERR);
        ValidationHelpers.validateRange(rating, FeedbackImpl.RATING_MIN, FeedbackImpl.RATING_MAX, RATING_ERR);

        return getRepository().addFeedback(creator,teamName, boardName, title, description, rating);
    }
}
