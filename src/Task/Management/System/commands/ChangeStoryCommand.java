package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;

import java.util.List;

public class ChangeStoryCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    public static final String INVALID_ID = "Invalid ID provided.";
    public static final String PROPERTY_UPDATED = "%s of story with ID %d has been changed to %s.";
    public static final String RECORD_ACTIVITY = "User %s changed the %s of story with ID %d to %s.";

    public ChangeStoryCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        //Change the Priority/Size/Status of a story.
        String nameOfCommandIssuer = parameters.get(0).trim();
        int storyID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_ID);
        String propertyToChange = parameters.get(2).trim();
        String newValue = parameters.get(3).toUpperCase();

        Story story = getRepository().getStories()
                .stream()
                .filter(e -> e.getID() == storyID)
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(INVALID_ID));

        switch (propertyToChange.toUpperCase()) {
            case "PRIORITY":
                Priority priority = ParsingHelpers.tryParseEnum(propertyToChange, Priority.class);
                story.setPriority(priority);
                break;
            case "SIZE":
                Size size = ParsingHelpers.tryParseEnum(propertyToChange, Size.class);
                story.setSize(size);
                break;
            case "STATUS":
                StoryStatus status = ParsingHelpers.tryParseEnum(propertyToChange, StoryStatus.class);
                story.setStatus(status);
                break;
            default:
                throw new InvalidUserInput("Invalid property to change has been provided.");
        }


        User user = getRepository().findUser(nameOfCommandIssuer);
        user.recordActivity(String.format(RECORD_ACTIVITY, nameOfCommandIssuer, propertyToChange, storyID, newValue));

        return String.format(PROPERTY_UPDATED, propertyToChange, storyID, newValue);
    }

}
