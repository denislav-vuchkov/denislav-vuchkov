package main.commands.task;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Story;
import main.models.tasks.enums.Priority;
import main.models.tasks.enums.Size;
import main.models.tasks.enums.StoryStatus;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

import java.util.List;

public class ChangeStory extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeStory(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        User changer = getRepository().findUser(parameters.get(0));
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Story story = getRepository().findStory(ID);
        getRepository().validateUserAndTaskFromSameTeam(changer.getName(), story.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        Team team = getRepository().findTeam(story);
        changer.log(String.format(TRY, changer.getName(), propertyToChange, "Story", ID, newValue), team.getName());

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
        changer.log(event, team.getName());
        return event;
    }
}
