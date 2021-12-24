package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.enums.FeedbackStatus;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ListFeedbacksFiltered extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_FEEDBACKS_EXIST = "No feedbacks to display.";
    public static final String INVALID_FILTER = "Feedback can only be filtered by status.";

    public ListFeedbacksFiltered(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String filter = parameters.get(0).split(":")[0].trim();
        String value = parameters.get(0).split(":")[1].trim();

        if (!filter.equalsIgnoreCase("Status")) {
            throw new InvalidUserInput(INVALID_FILTER);
        }

        FeedbackStatus status = ParsingHelpers.tryParseEnum(value, FeedbackStatus.class);

        List<Feedback> result = getRepository()
                .getFeedbacks()
                .stream()
                .filter(e -> e.getStatus().equals(status.toString()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return NO_FEEDBACKS_EXIST;
        }

        return result
                .stream()
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
