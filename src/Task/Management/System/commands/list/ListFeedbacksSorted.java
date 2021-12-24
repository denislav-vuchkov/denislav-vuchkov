package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListFeedbacksSorted extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_FEEDBACKS_EXIST = "There are no feedbacks in the system!";
    public static final String INVALID_PARAMETER_FOR_SORTING = "Feedback can only be sorted by title or rating.";

    public ListFeedbacksSorted(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (getRepository().getFeedbacks().isEmpty()) {
            return NO_FEEDBACKS_EXIST;
        }

        String criterion = parameters.get(0);

        switch (criterion.toUpperCase()) {
            case "TITLE":
                return getRepository().getFeedbacks()
                        .stream()
                        .sorted(Comparator.comparing(Feedback::getTitle))
                        .map(Feedback::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            case "RATING":
                return getRepository().getFeedbacks()
                        .stream()
                        .sorted(Comparator.comparing(Feedback::getRating).reversed())
                        .map(Feedback::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            default:
                throw new InvalidUserInput(INVALID_PARAMETER_FOR_SORTING);
        }
    }
}
