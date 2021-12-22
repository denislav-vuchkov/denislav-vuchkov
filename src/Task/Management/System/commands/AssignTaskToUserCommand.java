package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class AssignTaskToUserCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    public static final String INVALID_TASK_ID = "Invalid task ID provided";
    public static final String INVALID_TEAM = "Task is not contained in the boards of team %s.";
    public static final String INVALID_ASSIGNEE = "Task cannot be assigned to users out of the team";
    public static final String NEW_ASSIGNEE_DOES_NOT_EXIST = "New assignee does not exist";
    public static final String TASK_REASSIGNED = "Task with ID %d has now been reassigned to %s.";

    public AssignTaskToUserCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), "Task ID must be a number.");
        Team team = getRepository().findTeam(parameters.get(1));
        String newAssigneeName = parameters.get(2);

        AssignableTask task = getRepository().getAssignableTasks()
                        .stream().filter(e -> e.getID() == taskID)
                        .findFirst().orElseThrow(() -> new InvalidUserInput(INVALID_TASK_ID));


        //Checks if the task provided is contained in the team provided and returns link to the Board where it exists
        checkIfTaskIsContainedInTeam(task, team);
        //Checks if the new assignee is within the team the task is
        checkIfNewAssigneeIsValid(team, newAssigneeName);

        String currentAssigneeName = task.getAssignee();

        task.setAssignee(newAssigneeName);

        User currentAssignee = getRepository().findUser(currentAssigneeName);
        currentAssignee.unAssignTask(task);

        User newAssignee = getRepository().findUser(newAssigneeName);
        newAssignee.assignTask(task);

        return String.format(TASK_REASSIGNED, taskID, newAssignee);
    }

    private void checkIfTaskIsContainedInTeam(Task task, Team team) {
        List<Task> taskList = new ArrayList<>();
        for (Board board : team.getBoards()) {
            board.getTasks().forEach(e -> taskList.add(e));
        }

        taskList.stream()
                .filter(e -> e.getID()==task.getID())
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(String.format(INVALID_TEAM, team.getName())));
    }

    private void checkIfNewAssigneeIsValid(Team team, String newAssignee) {
        getRepository().getUsers()
                .stream()
                .filter(e -> e.getName().equals(newAssignee))
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(NEW_ASSIGNEE_DOES_NOT_EXIST));

        team.getUsers()
                .stream()
                .filter(e -> e.getName().equals(newAssignee))
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(INVALID_ASSIGNEE));
    }
}
