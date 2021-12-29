package Task.Management.System.models.teams;

import Task.Management.System.models.Event;
import Task.Management.System.models.EventLoggerImpl;
import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.utils.FormatHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.EventLogger.*;

public class BoardImpl implements Board {

    private String name;
    private final List<Task> tasks;
    private final EventLogger history;

    public BoardImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new EventLoggerImpl();
        history.addEvent(String.format(CREATION, BOARD, getName()));
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
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) {
        ValidationHelpers.entryNotAlreadyInList(task, getTasks(), getName());
        tasks.add(task);
        history.addEvent(String.format(BOARD_ADD_TASK, getName(), FormatHelpers.getType(task), task.getID()));
    }

    @Override
    public void removeTask(Task task) {
        ValidationHelpers.entryExistInList(task, getTasks(), getName());
        tasks.remove(task);
        history.addEvent(String.format(BOARD_REMOVE, getName(), FormatHelpers.getType(task), task.getID()));
    }

    @Override
    public List<Event> getLog() {
        return EventLogger.extract(history.getEvents(), getTasks());
    }

    @Override
    public String toString() {
        return String.format("Board name: %s - Board items: %d", getName(), getTasks().size());
    }
}
