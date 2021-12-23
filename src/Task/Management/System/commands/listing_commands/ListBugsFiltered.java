package Task.Management.System.commands.listing_commands;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListBugsFiltered extends BaseCommand {

    public static final int MIN_ARGUMENTS = 1;
    public static final int MAX_ARGUMENTS = 2;
    public static final String HEADER_FOR_ONE_FILTER = "--BUGS FILTERED BY %s--";
    public static final String HEADER_FOR_TWO_FILTERS = "--BUGS FILTERED BY %s AND %s--";
    public static final String INVALID_FILTERS_COUNT =
            String.format("Filters must be either no less than %d and no more than %d.", MIN_ARGUMENTS, MAX_ARGUMENTS);
    public static final String EMPTY_FILTERED_COLLECTION = "No bugs match the filtering criteria!";
    public static final String INVALID_FILTER_CRITERIA = "Cannot filter by the criteria requested. " +
            "Can only filter by status or assignee!";
    public static final String NO_BUGS_EXIST = "There are no bugs in the system!";

    public ListBugsFiltered(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateIntRange(parameters.size(),
                MIN_ARGUMENTS, MAX_ARGUMENTS, INVALID_FILTERS_COUNT);

        if (getRepository().getBugs().isEmpty()) {
            return NO_BUGS_EXIST;
        }

        StringBuilder bugsFiltered = new StringBuilder();

        switch(parameters.size()) {
            case 1:
                bugsFiltered.append(filterByOneParameter(parameters.get(0)));
                break;
            case 2:
                bugsFiltered.append(filterByTwoParameters(parameters.get(0), parameters.get(1)));
                break;
        }

        return bugsFiltered.toString();
    }

    private String filterByOneParameter(String filterOne) {
        String parameterToFilterBy = filterOne.split(":")[0].trim();
        String soughtValue = filterOne.split(":")[1];

        List<Bug> filteredCollection;

        switch (parameterToFilterBy.toUpperCase()) {
            case "STATUS":
                BugStatus status = ParsingHelpers.tryParseEnum(soughtValue, BugStatus.class);
                filteredCollection = getRepository()
                        .getBugs()
                        .stream()
                        .filter(e -> e.getStatus().equals(status.toString()))
                        .collect(Collectors.toList());
                break;
            case "ASSIGNEE":
                filteredCollection = getRepository()
                        .getBugs()
                        .stream()
                        .filter(e -> e.getAssignee().equalsIgnoreCase(soughtValue))
                        .collect(Collectors.toList());
                break;
            default:
                throw new InvalidUserInput(INVALID_FILTER_CRITERIA);
        }

        if (filteredCollection.isEmpty()) {
            return EMPTY_FILTERED_COLLECTION;
        }

        StringBuilder output = new StringBuilder();
        output.append(String.format(HEADER_FOR_ONE_FILTER, filterOne.toUpperCase())).append("\n");
        filteredCollection.forEach(e -> output.append(e.toString()).append("\n"));
        output.append(String.format(HEADER_FOR_ONE_FILTER, filterOne.toUpperCase()));

        return output.toString();
    }

    private String filterByTwoParameters(String filterOne, String filterTwo) {
        String firstParameterType = filterOne.split(":")[0].trim();
        String firstValue = filterOne.split(":")[1];

        String secondParameterType = filterTwo.split(":")[0].trim();
        String secondValue = filterTwo.split(":")[1];

        List<Bug> filteredCollection;
        BugStatus status;

        switch (firstParameterType.toUpperCase()) {
            case "STATUS":
                status = ParsingHelpers.tryParseEnum(firstValue, BugStatus.class);
                filteredCollection = getRepository()
                        .getBugs()
                        .stream()
                        .filter(e -> e.getStatus().equals(status.toString()))
                        .filter(e -> e.getAssignee().equalsIgnoreCase(secondValue))
                        .collect(Collectors.toList());
                break;
            case "ASSIGNEE":
                status = ParsingHelpers.tryParseEnum(secondValue, BugStatus.class);
                filteredCollection = getRepository()
                        .getBugs()
                        .stream()
                        .filter(e -> e.getAssignee().equalsIgnoreCase(firstValue))
                        .filter(e -> e.getStatus().equals(status.toString()))
                        .collect(Collectors.toList());
                break;
            default:
                throw new InvalidUserInput(INVALID_FILTER_CRITERIA);
        }

        if (filteredCollection.isEmpty()) {
            return EMPTY_FILTERED_COLLECTION;
        }

        StringBuilder output = new StringBuilder();

        String header = String.format(HEADER_FOR_TWO_FILTERS,
                firstParameterType.toUpperCase(), secondParameterType.toUpperCase());

        output.append(header).append("\n");
        filteredCollection.forEach(e -> output.append(e.toString()).append("\n"));
        output.append(header);

        return output.toString();
    }
}
