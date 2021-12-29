package Task.Management.System.commands.activity;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.Event;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTaskActivity extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTaskActivity(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        long ID = ParsingHelpers.tryParseLong(parameters.get(0), INVALID_ID);
        Task task = getRepository().findTask(ID);

        return getRepository()
                .findTask(ID)
                .getLog()
                .stream()
                .map(Event::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
