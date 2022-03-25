package main.commands.team;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.AssignableTask;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static main.utils.FormatHelpers.getType;

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
        Team team = getRepository().findTeam(task);
        String event = String.format(UNASSIGN, assigner.getName(), getType(task), task.getID());
        assigner.log(event, team.getName());

        if (!task.getAssignee().equals(UNASSIGNED)) {
            getRepository().findUser(task.getAssignee()).removeTask(task);
        }
        return event;
    }

        
    private String reassignTask(User assigner, String assigneeName, AssignableTask task) {
        if (task.getAssignee().equals(assigneeName)) throw new InvalidUserInput(String.format(SAME_USER, assigner));
        User assignee = getUserIfValid(assigneeName, task.getID());

        String event = String.format(REASSIGN, assigner.getName(), getType(task), task.getID(), assignee.getName());
        Team team = getRepository().findTeam(task);
        assigner.log(event, team.getName());
        if (!task.getAssignee().equals(UNASSIGNED)) {
            getRepository().findUser(task.getAssignee()).removeTask(task);
        }
        assignee.addTask(task);
        return event;
    }

    private User getUserIfValid(String assignerName, long ID) {
        getRepository().validateUserAndTaskFromSameTeam(assignerName, ID);
        return getRepository().findUser(assignerName);
    }
}
