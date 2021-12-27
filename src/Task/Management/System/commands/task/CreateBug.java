package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateBug extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 9;

    public CreateBug(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User creator = getRepository().findUser(parameters.get(0));
        String teamName = parameters.get(1);
        getRepository().validateUserIsFromTeam(creator.getName(), teamName);
        String boardName = parameters.get(2);
        String title = parameters.get(3);
        String description = parameters.get(4);
        List<String> steps = Arrays
                .stream(parameters.get(5).split(";"))
                .map(String::trim)
                .collect(Collectors.toList());
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(6), Priority.class);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(7), Severity.class);
        String assignee = parameters.get(8);

        creator.log(String.format(USER_CREATED_TASK, creator.getName(), "Bug", boardName));
        return getRepository().addBug(teamName, boardName, title, description, steps, priority, severity, assignee);
    }
}
