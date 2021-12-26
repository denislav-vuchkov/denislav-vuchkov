package Task.Management.System.commands.filter;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.utils.ListHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class FilterStories extends BaseCommand {

    public static final int MIN_ARGUMENTS = 1;
    public static final int MAX_ARGUMENTS = 2;
    public static final String INVALID_FILTERS_COUNT =
            String.format("Filters must be no less than %d and no more than %d.", MIN_ARGUMENTS, MAX_ARGUMENTS);

    public FilterStories(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateRange(parameters.size(),
                MIN_ARGUMENTS, MAX_ARGUMENTS, INVALID_FILTERS_COUNT);

        List<Story> result = ListHelpers.
                filterList(parameters.get(0), getRepository().getStories(), StoryStatus.class);

        if (parameters.size() == MAX_ARGUMENTS) {
            result = ListHelpers.filterList(parameters.get(1), result, StoryStatus.class);
        }

        if (result.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "stories");
        }

        return result
                .stream()
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
