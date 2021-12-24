package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListBugsSorted extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_BUGS_EXIST = "There are no bugs in the system!";
    public static final String INVALID_PARAMETER_FOR_SORTING = "Bug can only be sorted by title, priority or severity.";

    public ListBugsSorted(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (getRepository().getBugs().isEmpty()) {
            return NO_BUGS_EXIST;
        }

        String criterion = parameters.get(0);

        switch (criterion.toUpperCase()) {
            case "TITLE":
                return getRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getTitle))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            case "PRIORITY":
                return getRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getPriority))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            case "SEVERITY":
                return getRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getSeverity))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            default:
                throw new InvalidUserInput(INVALID_PARAMETER_FOR_SORTING);
        }
    }
}
