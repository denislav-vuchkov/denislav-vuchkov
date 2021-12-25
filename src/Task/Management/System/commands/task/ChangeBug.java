package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ChangeBug extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeBug(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0).trim());
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Bug bug = getRepository().findBug(ID);
        getRepository().validateUserAndTaskAreFromTheSameTeam(user.getName(), bug.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        switch (propertyToChange) {
            case "PRIORITY":
                Priority priority = ParsingHelpers.tryParseEnum(newValue, Priority.class);
                bug.setPriority(priority);
                break;
            case "SEVERITY":
                Severity severity = ParsingHelpers.tryParseEnum(newValue, Severity.class);
                bug.setSeverity(severity);
                break;
            case "STATUS":
                BugStatus status = ParsingHelpers.tryParseEnum(newValue, BugStatus.class);
                bug.setStatus(status);
                break;
            default:
                throw new InvalidUserInput(INVALID_PROPERTY);
        }

        String result = String.format(RECORD_ACTIVITY, user.getName(), propertyToChange, "Bug", ID, newValue);
        user.recordActivity(result);
        return result;
    }
}
