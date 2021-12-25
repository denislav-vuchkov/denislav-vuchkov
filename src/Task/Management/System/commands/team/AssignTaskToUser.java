package Task.Management.System.commands.team;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AssignTaskToUser extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    public static final String INVALID_TASK_ID = "Invalid task ID provided";
    public static final String TASK_REASSIGNED = "Task with ID %d has now been reassigned to %s.";
    public static final String USER_ACTIVITY = "User %s assigned task %d to %s.";

    public AssignTaskToUser(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User executorOfTask = getRepository().findUser(parameters.get(0));
        long taskID = ParsingHelpers.tryParseLong(parameters.get(1), "Task ID must be a number.");
        User newAssignee = getRepository().findUser(parameters.get(2));

        AssignableTask task = getRepository().getAssignableTasks()
                .stream().filter(e -> e.getID() == taskID)
                .findFirst().orElseThrow(() -> new InvalidUserInput(INVALID_TASK_ID));

        Team team = getRepository().findTeamWhereTaskIsLocated(task);
        getRepository().validateUserIsFromTeam(executorOfTask.getName(), team.getName());
        getRepository().validateUserIsFromTeam(newAssignee.getName(), team.getName());

        String currentAssigneeName = task.getAssignee();

        if (!currentAssigneeName.equals(UNASSIGNED)) {
            User currentAssignee = getRepository().findUser(currentAssigneeName);
            currentAssignee.removeTask(task);
        }

        newAssignee.addTask(task);

        executorOfTask.recordActivity(String.format(USER_ACTIVITY,
                executorOfTask.getName(), task.getID(), newAssignee.getName()));

        return String.format(TASK_REASSIGNED, taskID, newAssignee);
    }


}
