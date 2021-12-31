package Task.Management.System.commands.show;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowTaskDetails extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTaskDetails(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        long taskID = ParsingHelpers.tryParseLong(parameters.get(0), INVALID_ID);

        if (getRepository().getTasks().isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "tasks");
        }

        Task task = getRepository().findTask(taskID);

        return task.printDetails();
    }
}
