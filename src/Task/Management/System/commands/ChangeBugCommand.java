package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ChangeBugCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeBugCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User user = getRepository().findUser(parameters.get(0).trim());
        int bugID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_ID);
        String propertyToChange = parameters.get(2).trim();
        String newValue = parameters.get(3).toUpperCase();

        Bug bug = getRepository().findBug(bugID);

        switch (propertyToChange.toUpperCase()) {
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

        user.recordActivity(
                String.format(RECORD_ACTIVITY, user.getName(), propertyToChange, "Bug", bugID, newValue));

        return String.format(PROPERTY_UPDATED, propertyToChange, "Bug", bugID, newValue);
    }
}
