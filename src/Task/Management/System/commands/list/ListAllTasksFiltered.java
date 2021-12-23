package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ListAllTasksFiltered extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String HEADER = "--TASKS FILTERED BY TITLE--";
    public static final String EMPTY_FILTERED_COLLECTION = "No tasks match the filtering criteria!";
    public static final String NO_TASKS_EXIST = "There are no tasks in the system!";

    public ListAllTasksFiltered(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (getRepository().getTasks().isEmpty()) {
            return NO_TASKS_EXIST;
        }

        String textToFilterBy = parameters.get(0);

        List<Task> filteredTasks;

        filteredTasks = getRepository()
                .getTasks()
                .stream()
                .filter(e -> e.getTitle().contains(textToFilterBy))
                .collect(Collectors.toList());

        if (filteredTasks.isEmpty()) {
            return EMPTY_FILTERED_COLLECTION;
        }

        StringBuilder output = new StringBuilder();
        output.append(HEADER).append("\n");
        filteredTasks.forEach(bug -> output.append(bug.toString()).append("\n"));
        output.append(HEADER);

        return output.toString();
    }
}
