package Task_Management_System.commands.show;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.tasks.contracts.Task;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

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
