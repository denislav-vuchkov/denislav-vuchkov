package main.commands.activity;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.models.logger.EventImpl;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

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
        return getRepository()
                .findTask(ID)
                .getLog()
                .stream()
                .map(EventImpl::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
