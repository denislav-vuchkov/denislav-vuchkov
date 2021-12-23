package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ChangeStoryCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeStoryCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0).trim());
        int storyID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_ID);
        String propertyToChange = parameters.get(2).trim();
        String newValue = parameters.get(3).toUpperCase();

        Story story = getRepository().findStory(storyID);

        switch (propertyToChange.toUpperCase()) {
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

        user.recordActivity(
                String.format(RECORD_ACTIVITY, user.getName(), propertyToChange, "Story", storyID, newValue));

        return String.format(PROPERTY_UPDATED, propertyToChange, "Story", storyID, newValue);
    }
}
