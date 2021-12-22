package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;

import java.util.List;

public class ChangeStoryCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    public static final String INVALID_ID = "Bug ID must be a valid number";
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


        switch (propertyToChange.toUpperCase()) {
            case "PRIORITY":
                changePriority(storyID, newValue);
                break;
            case "SIZE":
                changeSize(storyID, newValue);
                break;
            case "STATUS":
                changeStatus(storyID, newValue);
                break;
            default:
                throw new InvalidUserInput("Invalid property to change has been provided.");

        }

        User user = getRepository().findUser(nameOfCommandIssuer);
        user.recordActivity(String.format(RECORD_ACTIVITY, nameOfCommandIssuer, propertyToChange, storyID, newValue));

        return String.format(PROPERTY_UPDATED, propertyToChange, storyID, newValue);
    }

    private void changePriority(int storyID, String newValue) {

    }

    private void changeSize(int storyID, String newValue) {

    }

    private void changeStatus(int storyID, String newValue) {
    }
}
