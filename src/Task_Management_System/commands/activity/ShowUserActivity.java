package Task_Management_System.commands.activity;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.logger.EventImpl;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowUserActivity extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowUserActivity(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String userName = parameters.get(0);
        return getRepository()
                .findByName(getRepository().getUsers(), parameters.get(0), USER)
                .getLog()
                .stream()
                .map(EventImpl::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
