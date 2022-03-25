package main.commands.task;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Bug;
import main.models.tasks.enums.BugStatus;
import main.models.tasks.enums.Priority;
import main.models.tasks.enums.Severity;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

import java.util.List;

public class ChangeBug extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    public ChangeBug(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        User changer = getRepository().findUser(parameters.get(0));
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Bug bug = getRepository().findBug(ID);
        getRepository().validateUserAndTaskFromSameTeam(changer.getName(), bug.getID());
        String propertyToChange = parameters.get(2).trim().toUpperCase();
        String newValue = parameters.get(3).toUpperCase();

        Team team = getRepository().findTeam(bug);
        changer.log(String.format(TRY, changer.getName(), propertyToChange, "Bug", ID, newValue), team.getName());

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
        changer.log(event, team.getName());
        return event;
    }
}
