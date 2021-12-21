package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.BugImpl;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateNewBugInTeamBoardCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 666;
    public static final String BUG_ADDED_TO_BOARD =
            "Bug with ID %d successfully added to board %s in team %s.";

    public CreateNewBugInTeamBoardCommand(TaskManagementSystemRepository repository) {
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
        Bug bug = new BugImpl(
                3,
                "Not Too Short",
                "Just Right Length",
                List.of("Nothing", "Works", "Help"),
                Priority.MEDIUM,
                Severity.MAJOR,
                "User10");

        board.addTask(bug);
        return String.format(BUG_ADDED_TO_BOARD, bug.getID(), board.getName(), team.getName());
    }
}
