package Task_Management_System.commands.sort;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.tasks.contracts.AssignableTask;
import Task_Management_System.utils.ListHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class SortAssignableTasks extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    public SortAssignableTasks(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        if (getRepository().getAssignableTasks().isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "tasks");
        }
        return ListHelpers.sortTasks(Comparator.comparing(AssignableTask::getTitle), getRepository().getAssignableTasks());
    }
}
