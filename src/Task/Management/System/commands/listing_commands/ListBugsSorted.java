package Task.Management.System.commands.listing_commands;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class ListBugsSorted extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String INVALID_PARAMETER_FOR_SORTING = "Bugs cannot be sorted by the provided parameter. " +
            "You can only sort by title, priority or severity.";
    public static final String HEADER = "--BUGS SORTED BY %s";
    public static final String NO_BUGS_EXIST = "There are no bugs in the system!";

    public ListBugsSorted(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (getRepository().getBugs().isEmpty()) {
            return NO_BUGS_EXIST;
        }

        String parameter = parameters.get(0);

        StringBuilder bugsSorted = new StringBuilder();

        bugsSorted.append(String.format(HEADER, parameter.toUpperCase())).append("\n");

        switch(parameter.toUpperCase()) {
            case "TITLE":
                getRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getTitle))
                        .forEach(bug -> bugsSorted.append(bug.toString()).append("\n"));
                break;
            case "PRIORITY":
                getRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getPriority))
                        .forEach(bug -> bugsSorted.append(bug.toString()).append("\n"));
                break;
            case "SEVERITY":
                getRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getSeverity))
                        .forEach(bug -> bugsSorted.append(bug.toString()).append("\n"));
                break;
            default:
                throw new InvalidUserInput(INVALID_PARAMETER_FOR_SORTING);
        }

        bugsSorted.append(String.format(HEADER, parameter.toUpperCase()));

        return bugsSorted.toString();
    }

}
