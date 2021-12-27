package Task.Management.System.commands.team;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AssignTask extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public static final String ASSIGN_EVENT = "User %s assigned %s with ID %d to %s.";

    public AssignTask(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User assigner = getRepository().findUser(parameters.get(0));
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        AssignableTask task = getRepository().findAssignableTask(ID);
        User newAssignee = getRepository().findUser(parameters.get(2));
        getRepository().validateUserAndTaskFromSameTeam(assigner.getName(), task.getID());
        getRepository().validateUserAndTaskFromSameTeam(newAssignee.getName(), task.getID());

        if (!task.getAssignee().equals(UNASSIGNED)) {
            User oldAssignee = getRepository().findUser(task.getAssignee());
            oldAssignee.removeTask(task);
        }

        newAssignee.addTask(task);

        String taskType = task.getClass().getSimpleName().replace("Impl", "");
        assigner.log(String.format(ASSIGN_EVENT, assigner.getName(), taskType, task.getID(), newAssignee.getName()));
        return String.format(ASSIGN_EVENT, assigner.getName(), taskType, task.getID(), newAssignee.getName());
    }
}
