package Task.Management.System.commands.filter;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.utils.ListHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class FilterFeedbacks extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public FilterFeedbacks(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String filter = parameters.get(0).split(":")[0].trim();
        String value = parameters.get(0).split(":")[1].trim();

        if (!filter.equalsIgnoreCase("Status")) {
            throw new InvalidUserInput(String.format(INVALID_FILTER, "Feedbacks", "status"));
        }

        List<Feedback> result = ListHelpers.
                filterByStatus(value, getRepository().getFeedbacks(), FeedbackStatus.class);

        if (result.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "feedbacks");
        }

        return result
                .stream()
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
