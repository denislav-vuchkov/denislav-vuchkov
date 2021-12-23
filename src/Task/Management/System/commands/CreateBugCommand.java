package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateBugCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 9;

    public CreateBugCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //String teamName, String boardName, String title, String description, List<String> steps,
        //Priority priority, Severity severity, String assignee
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0));
        String teamName = parameters.get(1);
        String boardName = parameters.get(2);
        String title = parameters.get(3);
        String description = parameters.get(4);
        List<String> stepsToReproduce = Arrays.stream(parameters.get(5).split("[!?;\\.] "))
                .collect(Collectors.toList());
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(6), Priority.class);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(7), Severity.class);
        String assignee = parameters.get(8);

        user.recordActivity(String.format(USER_CREATED_TASK, user.getName(), "Bug", boardName));

        return getRepository().addBug(teamName, boardName, title,
                description, stepsToReproduce, priority, severity, assignee);
    }
}
