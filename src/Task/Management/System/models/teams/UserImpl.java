package Task.Management.System.models.teams;

import Task.Management.System.models.ChangesLoggerImpl;
import Task.Management.System.models.contracts.ChangesLogger;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.ChangesLogger.CREATION_MESSAGE;

public class UserImpl implements User {

    public static final String USER_TASK_ASSIGNED = "%s with ID %d assigned to user %s.";
    public static final String USER_TASK_UNASSIGNED = "%s with ID %d unassigned from user %s.";

    public static final String TASK_ALREADY_ASSIGNED_TO_USER = "%s with %d is already assigned to user %s";
    public static final String TASK_NOT_ASSIGNED_TO_USER = "%s with %d is not assigned to user %s";

    public static final String HISTORY_HEADER = "--HISTORY--";

    private final ChangesLogger historyOfChanges;
    private final List<AssignableTask> assignedTasks;
    private String name;

    public UserImpl(String name) {
        setName(name);
        assignedTasks = new ArrayList<>();
        historyOfChanges = new ChangesLoggerImpl();
        historyOfChanges.addChange(
                String.format(CREATION_MESSAGE,
                        getClass().getSimpleName().replace("Impl", " "),
                        getName()));
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateIntRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, INVALID_NAME_MESSAGE);
        this.name = name;
    }

    @Override
    public List<AssignableTask> getAssignedTasks() {
        return new ArrayList<>(assignedTasks);
    }

    @Override
    public void recordActivity(String description) {
        historyOfChanges.addChange(description);
    }

    @Override
    public void assignTask(AssignableTask task) {
        if (assignedTasks.contains(task)) {
            throw new InvalidUserInput(
                    String.format(TASK_ALREADY_ASSIGNED_TO_USER,
                            task.getClass().getSimpleName().replace("Impl", ""),
                            task.getID(),
                            getName()));
        }

        historyOfChanges.addChange(
                String.format(USER_TASK_ASSIGNED,
                        task.getClass().getSimpleName().replace("Impl", ""),
                        task.getID(),
                        getName()));

        task.setAssignee(getName());
        assignedTasks.add(task);
    }

    @Override
    public void unAssignTask(AssignableTask task) {
        if (!assignedTasks.contains(task)) {
            throw new InvalidUserInput(
                    String.format(TASK_NOT_ASSIGNED_TO_USER,
                            task.getClass().getSimpleName().replace("Impl", ""),
                            task.getID(),
                            getName()));
        }

        historyOfChanges.addChange(
                String.format(USER_TASK_UNASSIGNED,
                        task.getClass().getSimpleName().replace("Impl", ""),
                        task.getID(),
                        getName()));

        task.unAssign();
        assignedTasks.remove(task);
    }

    @Override
    public String getHistory() {
        return String.format("%s%n%s%s",
                HISTORY_HEADER,
                historyOfChanges.getCompleteHistory(),
                HISTORY_HEADER);
    }

    @Override
    public String toString() {
        return String.format("Username: %s - Tasks: %d",
                getName(),
                getAssignedTasks().size());
    }
}
