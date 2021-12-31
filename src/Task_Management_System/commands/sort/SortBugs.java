package Task_Management_System.commands.sort;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Bug;
import Task_Management_System.utils.ListHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class SortBugs extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public SortBugs(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        List<Bug> bugs = getRepository().getBugs();

        if (bugs.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "bugs");
        }

        String criterion = parameters.get(0);

        switch (criterion.toUpperCase()) {
            case "TITLE":
                return ListHelpers.sort(Comparator.comparing(Bug::getTitle), bugs);
            case "PRIORITY":
                return ListHelpers.sort(Comparator.comparing(Bug::getPriority), bugs);
            case "SEVERITY":
                return ListHelpers.sort(Comparator.comparing(Bug::getSeverity), bugs);
            default:
                throw new InvalidUserInput(String.format(INVALID_SORT_PARAMETER, "Bugs", "title, priority or severity"));
        }
    }
}
