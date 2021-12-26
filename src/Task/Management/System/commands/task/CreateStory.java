package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateStory extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 8;

    public CreateStory(TaskManagementSystemRepository repository) {
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
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(5), Priority.class);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(6), Size.class);
        String assignee = parameters.get(7);

        creator.recordActivity(String.format(USER_CREATED_TASK, creator.getName(), "Story", boardName));
        return getRepository().addStory(teamName, boardName, title, description, priority, size, assignee);
    }
}
