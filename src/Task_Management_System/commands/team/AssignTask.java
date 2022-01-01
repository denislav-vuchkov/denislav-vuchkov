package Task_Management_System.commands.team;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.AssignableTask;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Task_Management_System.utils.FormatHelpers.getType;

public class AssignTask extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public static final String REASSIGN = "User %s: Reassigned %s with ID %d to %s.";
    public static final String UNASSIGN = "User %s: Set %s with ID %d to unassigned.";
    public static final String SAME_USER = "Task is already assigned to %s.";

    public AssignTask(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String assignerName = parameters.get(0);
        List<Long> taskIDs = Arrays
                .stream(parameters.get(1).split(";"))
                .map(task -> ParsingHelpers.tryParseLong(task, INVALID_ID))
                .collect(Collectors.toList());
        String assigneeName = parameters.get(2);

        StringBuilder result = new StringBuilder();
        for (Long ID : taskIDs) {
            User assigner = getUserIfValid(assignerName, ID);
            AssignableTask task = getRepository().findAssignableTask(ID);
            if (assigneeName.isBlank()) {
                result.append(unassignTask(assigner, task)).append(System.lineSeparator());
            } else {
                result.append(reassignTask(assigner, assigneeName, task)).append(System.lineSeparator());
            }
        }
        return result.toString().trim();
    }

    private String unassignTask(User assigner, AssignableTask task) {
        String event = String.format(UNASSIGN, assigner.getName(), getType(task), task.getID());
        assigner.log(event);

        if (!task.getAssignee().equals(UNASSIGNED)) {
            getRepository().findByName(getRepository().getUsers(), task.getAssignee(), USER).removeTask(task);
        }
        return event;
    }

    private String reassignTask(User assigner, String assigneeName, AssignableTask task) {
        if (task.getAssignee().equals(assigneeName)) throw new InvalidUserInput(String.format(SAME_USER, assigner));
        User assignee = getUserIfValid(assigneeName, task.getID());

        String event = String.format(REASSIGN, assigner.getName(), getType(task), task.getID(), assignee.getName());
        assigner.log(event);
        if (!task.getAssignee().equals(UNASSIGNED)) {
            getRepository().findByName(getRepository().getUsers(), task.getAssignee(), USER).removeTask(task);
        }
        assignee.addTask(task);
        return event;
    }

    private User getUserIfValid(String assignerName, long ID) {
        getRepository().validateUserAndTaskFromSameTeam(assignerName, ID);
        return getRepository().findByName(getRepository().getUsers(), assignerName, USER);
    }
}
