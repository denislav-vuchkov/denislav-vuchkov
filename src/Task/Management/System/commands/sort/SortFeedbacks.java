package Task.Management.System.commands.sort;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.utils.ListHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class SortFeedbacks extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public SortFeedbacks(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        List<Feedback> feedbacks = getRepository().getFeedbacks();

        if (feedbacks.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "feedbacks");
        }

        String criterion = parameters.get(0);

        switch (criterion.toUpperCase()) {
            case "TITLE":
                return ListHelpers.sort(Comparator.comparing(Feedback::getTitle), feedbacks);
            case "RATING":
                return ListHelpers.sort((Comparator.comparing(Feedback::getRating).reversed()), feedbacks);
            default:
                throw new InvalidUserInput(String.format(INVALID_SORT_PARAMETER, "Feedbacks", "title or rating"));
        }
    }
}
