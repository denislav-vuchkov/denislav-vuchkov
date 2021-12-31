package Task_Management_System.commands.task;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Story;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Size;
import Task_Management_System.models.tasks.enums.StoryStatus;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;

public class ChangeStory extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeStory(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User changer = getRepository().findByName(getRepository().getUsers(), parameters.get(0), USER);
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Story story = getRepository().findStory(ID);
        getRepository().validateUserAndTaskFromSameTeam(changer.getName(), story.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        changer.log(String.format(ATTEMPTED_CHANGE, changer.getName(), propertyToChange, "Story", ID, newValue));

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

        String event = String.format(SUCCESSFUL_CHANGE, changer.getName(), propertyToChange, "Story", ID, newValue);
        changer.log(event);
        return event;
    }
}
