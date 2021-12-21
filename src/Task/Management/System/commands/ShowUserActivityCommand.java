package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowUserActivityCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowUserActivityCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        String name = parameters.get(0);
        //Member member = getTaskManagementSystemRepository().findByName();
     //   getTaskManagementSystemRepository().addNewMember(member);
        return null;

    }
}
