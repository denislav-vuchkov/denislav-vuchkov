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
    public static final String HEADER = "--FEEDBACKS FILTERED BY STATUS--";
    public static final String EMPTY_FILTERED_COLLECTION = "No feedbacks match the filtering criteria!";
    public static final String INVALID_FILTER_PROVIDED = "Cannot filter feedbacks by anything but status!";
    public static final String NO_FEEDBACKS_EXIST = "There are no feedbacks in the system!";

    public ListFeedbacksFiltered(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (getRepository().getFeedbacks().isEmpty()) {
            return NO_FEEDBACKS_EXIST;
        }

        String parameterToFilterBy = parameters.get(0).split(":")[0].trim();
        String soughtValue = parameters.get(0).split(":")[1];

        if (!parameterToFilterBy.equalsIgnoreCase("Status")) {
            throw new InvalidUserInput(INVALID_FILTER_PROVIDED);
        }

        List<Feedback> filteredCollection;

        FeedbackStatus status = ParsingHelpers.tryParseEnum(soughtValue, FeedbackStatus.class);
        filteredCollection = getRepository()
                .getFeedbacks()
                .stream()
                .filter(e -> e.getStatus().equals(status.toString()))
                .collect(Collectors.toList());

        if (filteredCollection.isEmpty()) {
            return EMPTY_FILTERED_COLLECTION;
        }

        StringBuilder output = new StringBuilder();
        output.append(HEADER).append("\n");
        filteredCollection.forEach(e -> output.append(e.toString()).append("\n"));
        output.append(HEADER);

        return output.toString();
    }

}
