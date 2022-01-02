package Task_Management_System.commands;

import Task_Management_System.commands.contracts.Command;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public abstract class BaseCommand implements Command {

    public static final String INVALID_ID = "Invalid task ID.";
    public static final String TRY = "User %s: Attempted to modify the %s of %s with ID %d to %s.";
    public static final String SUCCESSFUL_CHANGE = "User %s: Successfully changed the %s of %s with ID %d to %s.";
    public static final String INVALID_PROPERTY = "Invalid property to change has been provided.";
    public static final String RATING_ERR = "Feedback rating should be a numeric value between 0 and 10!";
    public static final String CREATE = "User %s: Added a new %s with ID %d in board %s.";
    public static final String UNASSIGNED = "Unassigned";
    public static final String NO_ITEMS_TO_DISPLAY = "No %s to display.";
    public static final String INVALID_FILTER = "%s can only be filtered by %s.";
    public static final String INVALID_SORT_PARAMETER = "%s can only be sorted by %s.";
    public static final String TEAM = "team";
    public static final String BOARD = "board";
    public static final String USER = "user";

    private final TaskManagementSystemRepository repository;

    protected BaseCommand(TaskManagementSystemRepository repository) {
        this.repository = repository;
    }

    protected TaskManagementSystemRepository getRepository() {
        return repository;
    }

    @Override
    public String execute(List<String> parameters) {
        return executeCommand(parameters);
    }

    protected abstract String executeCommand(List<String> parameters);

}
