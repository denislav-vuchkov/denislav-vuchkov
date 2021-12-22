package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateStoryCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;
    public static final String UNASSIGNED = "Unassigned";

    public CreateStoryCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //String teamName, String boardName, String title, String description, Priority priority, Size size, String assignee
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        String boardName = parameters.get(1);
        String title = parameters.get(2);
        String description = parameters.get(3);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(4), Priority.class);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(5), Size.class);
        String assignee = parameters.get(6).isEmpty() ? UNASSIGNED : parameters.get(6);

        return getRepository().addStory(teamName, boardName, title, description, priority, size, assignee);
    }
}
