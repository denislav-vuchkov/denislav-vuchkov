package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AssignTaskToUserCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String INVALID_TASK_ID = "Invalid task ID provided";
    public static final String INVALID_TEAM = "Task is not contained in the boards of team %s.";
    public static final String TASK_REASSIGNED = "Task with ID %d has now been reassigned to %s.";

    public AssignTaskToUserCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        long taskID = ParsingHelpers.tryParseLong(parameters.get(0), "Task ID must be a number.");
        String newAssigneeName = parameters.get(1);

        AssignableTask task = getRepository().getAssignableTasks()
                .stream().filter(e -> e.getID() == taskID)
                .findFirst().orElseThrow(() -> new InvalidUserInput(INVALID_TASK_ID));

        Team team = findTeamWhereTaskIsLocated(task);

        getRepository().validateUserIsFromTeam(newAssigneeName, team.getName());

        String currentAssigneeName = task.getAssignee();

        if (!currentAssigneeName.equals(UNASSIGNED)) {
            User currentAssignee = getRepository().findUser(currentAssigneeName);
            currentAssignee.removeTask(task);
        }

        User newAssignee = getRepository().findUser(newAssigneeName);
        newAssignee.addTask(task);

        return String.format(TASK_REASSIGNED, taskID, newAssignee);
    }

    private Team findTeamWhereTaskIsLocated(Task task) {
        List<Team> teamsList = getRepository().getTeams();

        Team targetTeam = teamsList.get(0);

        for (Team team : teamsList) {
            if (checkIfTaskIsInTeam(team, task)) {
                targetTeam = team;
            }
        }

        return targetTeam;
    }

    private boolean checkIfTaskIsInTeam(Team team, Task task) {
        for (Board board : team.getBoards()) {
            if (board.getTasks().contains(task)) {
                return true;
            }
        }

        return false;
    }
}
