package Task_Management_System.commands.show;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.tasks.contracts.Task;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowAllTasks extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    public ShowAllTasks(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        List<Task> tasks = getRepository().getTasks();
        if (tasks.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "tasks");
        }

        return tasks
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
