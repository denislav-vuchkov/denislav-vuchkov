package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateStoryCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 8;

    public CreateStoryCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //String teamName, String boardName, String title, String description, Priority priority, Size size, String assignee
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0));
        String teamName = parameters.get(1);
        getRepository().validateUserIsFromTeam(user.getName(), teamName);
        String boardName = parameters.get(2);
        String title = parameters.get(3);
        String description = parameters.get(4);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(5), Priority.class);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(6), Size.class);
        String assignee = parameters.get(7);

        user.recordActivity(String.format(USER_CREATED_TASK, user.getName(), "Story", boardName));

        return getRepository().addStory(teamName, boardName, title, description, priority, size, assignee);
    }
}
