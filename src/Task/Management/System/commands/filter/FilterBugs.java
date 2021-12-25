package Task.Management.System.commands.filter;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.utils.FiltrationHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class FilterBugs extends BaseCommand {

    public static final int MIN_ARGUMENTS = 1;
    public static final int MAX_ARGUMENTS = 2;
    public static final String INVALID_FILTERS_COUNT =
            String.format("Filters must be no less than %d and no more than %d.", MIN_ARGUMENTS, MAX_ARGUMENTS);

    public FilterBugs(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateIntRange(parameters.size(),
                MIN_ARGUMENTS, MAX_ARGUMENTS, INVALID_FILTERS_COUNT);

        List<Bug> result = FiltrationHelpers.
                filterList(parameters.get(0), getRepository().getBugs(), BugStatus.class);

        if (parameters.size() == MAX_ARGUMENTS) {
            result = FiltrationHelpers.
                    filterList(parameters.get(1), result, BugStatus.class);
        }

        if (result.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "bugs");
        }

        return result
                .stream()
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
