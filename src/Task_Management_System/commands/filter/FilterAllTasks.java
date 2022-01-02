package Task_Management_System.commands.filter;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Task;
import Task_Management_System.utils.ListHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class FilterAllTasks extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public FilterAllTasks(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String filter = parameters.get(0).split(":")[0].trim();
        String value = parameters.get(0).split(":")[1].trim();

        if (!filter.equalsIgnoreCase("Title")) {
            throw new InvalidUserInput(String.format(INVALID_FILTER, "All tasks", "title"));
        }

        List<Task> result = ListHelpers.
                filterByTitle(value, getRepository().getTasks());

        if (result.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "tasks");
        }

        return result
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
