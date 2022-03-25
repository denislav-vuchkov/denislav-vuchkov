package main.commands.filter;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.models.tasks.contracts.AssignableTask;
import main.utils.ListHelpers;
import main.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class FilterAssignableTasks extends BaseCommand {

    public static final int MIN_ARGUMENTS = 1;
    public static final int MAX_ARGUMENTS = 2;
    public static final String INVALID_FILTERS_COUNT =
            String.format("Filters must be no less than %d and no more than %d.", MIN_ARGUMENTS, MAX_ARGUMENTS);

    public FilterAssignableTasks(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateRange(parameters.size(), MIN_ARGUMENTS, MAX_ARGUMENTS, INVALID_FILTERS_COUNT);
        ValidationHelpers.validateFilterParameters(parameters);

        List<AssignableTask> result = ListHelpers.
                filterTasks(parameters.get(0), getRepository().getAssignableTasks());

        if (parameters.size() == MAX_ARGUMENTS) {
            result = ListHelpers.filterTasks(parameters.get(1), result);
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
