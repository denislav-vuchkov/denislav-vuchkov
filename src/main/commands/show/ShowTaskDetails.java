package main.commands.show;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

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

        return getRepository().findTask(taskID).printDetails();
    }
}
