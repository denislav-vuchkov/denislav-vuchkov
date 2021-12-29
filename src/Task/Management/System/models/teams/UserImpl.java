package Task.Management.System.models.teams;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.Event;
import Task.Management.System.models.EventLoggerImpl;
import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.EventLogger.*;

public class UserImpl implements User {

    public static final String ALREADY_ASSIGNED = "%s with %d is already assigned to user %s";
    public static final String NOT_ASSIGNED = "%s with %d is not assigned to user %s";
    private final List<AssignableTask> tasks;
    private final EventLogger history;
    private String name;

    public UserImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new EventLoggerImpl();
        history.addEvent(String.format(CREATION, "User", getName()));
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, INVALID_NAME_MESSAGE);
        this.name = name;
    }

    @Override
    public List<AssignableTask> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(AssignableTask task) {
        String taskType = task.getClass().getSimpleName().replace("Impl", "");

        if (tasks.contains(task)) {
            throw new InvalidUserInput(String.format(ALREADY_ASSIGNED, USER, task.getID(), getName()));
        }

        tasks.add(task);
        history.addEvent(String.format(TASK_ASSIGNED, taskType, task.getID(), getName()));
        task.setAssignee(getName());
    }

    @Override
    public void removeTask(AssignableTask task) {
        String taskType = task.getClass().getSimpleName().replace("Impl", "");

        if (!tasks.contains(task)) {
            throw new InvalidUserInput(String.format(NOT_ASSIGNED, taskType, task.getID(), getName()));
        }

        task.unAssign();
        tasks.remove(task);
        history.addEvent(String.format(TASK_UNASSIGNED, taskType, task.getID(), getName()));
    }

    public void log(String description) {
        history.addEvent(description);
    }

    @Override
    public List<Event> getLog() {
        return new ArrayList<>(history.getEvents());
    }

    @Override
    public String toString() {
        return String.format("User: %s - Tasks: %d", getName(), getTasks().size());
    }
}
