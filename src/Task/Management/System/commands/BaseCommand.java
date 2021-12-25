package Task.Management.System.commands;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public abstract class BaseCommand implements Command {

    public static final String INVALID_ID = "Invalid ID provided.";
    public static final String RECORD_ACTIVITY = "User %s changed the %s of %s with ID %d to %s.";
    public static final String INVALID_PROPERTY = "Invalid property to change has been provided.";
    public static final String FEEDBACK_RATING_ERROR = "Feedback rating should be a numeric value between 0 and 10!";
    public static final String USER_CREATED_TASK = "User %s created a new %s in board %s.";
    public static final String UNASSIGNED = "Unassigned";
    public static final String NO_ITEMS_TO_DISPLAY = "No %s to display.";
    public static final String INVALID_FILTER = "%s can only be filtered by %s.";
    public static final String INVALID_SORT_PARAMETER = "%s can only be sorted by %s.";

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
