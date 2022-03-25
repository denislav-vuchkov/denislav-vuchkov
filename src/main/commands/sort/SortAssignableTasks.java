package main.commands.sort;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.models.tasks.contracts.AssignableTask;
import main.utils.ListHelpers;
import main.utils.ValidationHelpers;

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
