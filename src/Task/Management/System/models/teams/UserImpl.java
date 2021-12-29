package Task.Management.System.models.teams;

import Task.Management.System.models.Event;
import Task.Management.System.models.EventLoggerImpl;
import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.FormatHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.EventLogger.*;

public class UserImpl implements User {

    private final List<AssignableTask> tasks;
    private final EventLogger history;
    private String name;

    public UserImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new EventLoggerImpl();
        history.addEvent(String.format(CREATION, FormatHelpers.getType(this), getName()));
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateRange(name.length(), NAME_MIN_LEN, NAME_MAX_LEN, INVALID_NAME);
        this.name = name;
    }

    @Override
    public List<AssignableTask> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(AssignableTask task) {
        ValidationHelpers.entryNotAlreadyInList(task, getTasks(), getName());
        tasks.add(task);
        history.addEvent(String.format(USER_ADD_TASK, getName(), FormatHelpers.getType(task), task.getID()));
        task.setAssignee(getName());
    }

    @Override
    public void removeTask(AssignableTask task) {
        ValidationHelpers.entryExistInList(task, getTasks(), getName());
        tasks.remove(task);
        history.addEvent(String.format(USER_REMOVE_TASK, getName(), FormatHelpers.getType(task), task.getID()));
        task.unAssign();
    }

    public void log(String description) {
        history.addEvent(description);
    }

    @Override
    public List<Event> getLog() {
        return EventLogger.extract(history.getEvents());
    }

    @Override
    public String toString() {
        return String.format("User: %s - Tasks: %d", getName(), getTasks().size());
    }
}
