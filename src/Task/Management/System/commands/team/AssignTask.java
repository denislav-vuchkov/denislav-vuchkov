package Task.Management.System.commands.team;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AssignTask extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public static final String ASSIGN_EVENT = "User %s: Reassigned %s with ID %d to %s.";
    public static final String CANNOT_REASSIGN_TO_SAME_USER = "Task is already assigned to %s.";

    public AssignTask(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User assigner = getRepository().findByName(getRepository().getUsers(), parameters.get(0), USER);
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        AssignableTask task = getRepository().findAssignableTask(ID);
        User newAssignee = getRepository().findByName(getRepository().getUsers(), parameters.get(2), USER);

        getRepository().validateUserAndTaskFromSameTeam(assigner.getName(), task.getID());
        getRepository().validateUserAndTaskFromSameTeam(newAssignee.getName(), task.getID());

        if (task.getAssignee().equals(newAssignee.getName())) {
            throw new InvalidUserInput(String.format(CANNOT_REASSIGN_TO_SAME_USER, assigner));
        }

        String taskType = task.getClass().getSimpleName().replace("Impl", "");

        assigner.log(String.format(ASSIGN_EVENT, assigner.getName(), taskType, task.getID(), newAssignee.getName()));

        if (!task.getAssignee().equals(UNASSIGNED)) {
            getRepository().findByName(getRepository().getUsers(), task.getAssignee(), USER).removeTask(task);
        }

        newAssignee.addTask(task);

        return String.format(ASSIGN_EVENT, assigner.getName(), taskType, task.getID(), newAssignee.getName());
    }
}
