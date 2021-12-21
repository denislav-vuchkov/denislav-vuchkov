package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.StoryImpl;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateNewStoryInTeamBoardCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 666;
    public static final String STORY_ADDED_TO_BOARD =
            "Story with ID %d successfully added to board %s in team %s.";

    public CreateNewStoryInTeamBoardCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(0));

        Board board = team
                .getBoards()
                .stream()
                .filter(b -> b.getName().equals(parameters.get(1)))
                .findAny().orElseThrow();

        //TODO
        Story story = new StoryImpl(
                1111,
                "Not Too Short",
                "Just Right Length",
                Priority.HIGH,
                Size.LARGE);

        board.addTask(story);
        return String.format(STORY_ADDED_TO_BOARD, story.getID(), board.getName(), team.getName());
    }
}
