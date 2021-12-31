package Task_Management_System.commands.task;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Bug;
import Task_Management_System.models.tasks.enums.BugStatus;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Severity;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;

public class ChangeBug extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeBug(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User changer = getRepository().findByName(getRepository().getUsers(), parameters.get(0), USER);
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Bug bug = getRepository().findBug(ID);
        getRepository().validateUserAndTaskFromSameTeam(changer.getName(), bug.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        changer.log(String.format(ATTEMPTED_CHANGE, changer.getName(), propertyToChange, "Bug", ID, newValue));

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

        String event = String.format(SUCCESSFUL_CHANGE, changer.getName(), propertyToChange, "Bug", ID, newValue);
        changer.log(event);
        return event;
    }
}
