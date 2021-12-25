package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ChangeStory extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeStory(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0).trim());
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Story story = getRepository().findStory(ID);
        getRepository().validateUserAndTaskAreFromTheSameTeam(user.getName(), story.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        switch (propertyToChange) {
            case "PRIORITY":
                Priority priority = ParsingHelpers.tryParseEnum(newValue, Priority.class);
                story.setPriority(priority);
                break;
            case "SIZE":
                Size size = ParsingHelpers.tryParseEnum(newValue, Size.class);
                story.setSize(size);
                break;
            case "STATUS":
                StoryStatus status = ParsingHelpers.tryParseEnum(newValue, StoryStatus.class);
                story.setStatus(status);
                break;
            default:
                throw new InvalidUserInput(INVALID_PROPERTY);
        }

        String result = String.format(RECORD_ACTIVITY, user.getName(), propertyToChange, "Story", ID, newValue);
        user.recordActivity(result);
        return result;
    }
}
