package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListStoriesSorted extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_STORIES_EXIST = "There are no stories in the system!";
    public static final String INVALID_PARAMETER_FOR_SORTING = "Stories cannot be sorted by the provided parameter. " +
            "You can only sort by title, priority or size.";

    public ListStoriesSorted(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (getRepository().getStories().isEmpty()) {
            return NO_STORIES_EXIST;
        }

        String criterion = parameters.get(0);

        switch (criterion.toUpperCase()) {
            case "TITLE":
                return getRepository().getStories()
                        .stream()
                        .sorted(Comparator.comparing(Story::getTitle))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            case "PRIORITY":
                return getRepository().getStories()
                        .stream()
                        .sorted(Comparator.comparing(Story::getPriority))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            case "SIZE":
                return getRepository().getStories()
                        .stream()
                        .sorted(Comparator.comparing(Story::getSize))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            default:
                throw new InvalidUserInput(INVALID_PARAMETER_FOR_SORTING);
        }
    }
}
