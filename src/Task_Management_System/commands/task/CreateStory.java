package Task_Management_System.commands.task;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Size;
import Task_Management_System.models.teams.contracts.Board;
import Task_Management_System.models.teams.contracts.Team;
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
        User creator = getRepository().findUser(parameters.get(0));
        Team team = getRepository().findTeam(parameters.get(1));
        getRepository().validateUserIsFromTeam(creator.getName(), team.getName());
        Board board = getRepository().findBoard(parameters.get(2), team.getName());
        String title = parameters.get(3);
        String description = parameters.get(4);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(5), Priority.class);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(6), Size.class);
        String assignee = parameters.get(7);
        if (!assignee.isBlank()) getRepository().validateUserIsFromTeam(assignee, team.getName());
        return getRepository().addStory(creator, team, board, title, description, priority, size, assignee);
    }
}
