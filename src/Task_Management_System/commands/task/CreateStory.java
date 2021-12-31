package Task_Management_System.commands.task;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Size;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;

public class CreateStory extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 8;

    public CreateStory(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User creator = getRepository().findByName(getRepository().getUsers(), parameters.get(0), USER);
        String teamName = parameters.get(1);
        getRepository().validateUserIsFromTeam(creator.getName(), teamName);
        String boardName = parameters.get(2);
        String title = parameters.get(3);
        String description = parameters.get(4);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(5), Priority.class);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(6), Size.class);
        String assignee = parameters.get(7);

        return getRepository().addStory(creator,teamName, boardName, title, description, priority, size, assignee);
    }
}
