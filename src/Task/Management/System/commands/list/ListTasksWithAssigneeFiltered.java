package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ListTasksWithAssigneeFiltered extends BaseCommand {

    public static final int MIN_ARGUMENTS = 1;
    public static final int MAX_ARGUMENTS = 2;
    public static final String INVALID_FILTERS_COUNT =
            String.format("Filters must be no less than %d and no more than %d.", MIN_ARGUMENTS, MAX_ARGUMENTS);

    public ListTasksWithAssigneeFiltered(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateIntRange(parameters.size(),
                MIN_ARGUMENTS, MAX_ARGUMENTS, INVALID_FILTERS_COUNT);

        List<AssignableTask> result = getRepository().
                getFilteredList(parameters.get(0), getRepository().getAssignableTasks());

        if (parameters.size() == MAX_ARGUMENTS) {
            result = getRepository().getFilteredList(parameters.get(1), result);
        }

        if (result.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "assignable tasks");
        }

        return result
                .stream()
                .map(AssignableTask::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
