package Task.Management.System.models.teams;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.Event;
import Task.Management.System.models.EventLoggerImpl;
import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static Task.Management.System.models.contracts.EventLogger.*;

public class BoardImpl implements Board {

    public static final String ALREADY_EXIST = "%s with %d already exists in board %s";
    public static final String NOT_EXISTS = "%s with %d does not exist in board %s";
    private final List<Task> tasks;
    private final EventLogger history;
    private String name;

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
        ValidationHelpers.validateRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, INVALID_NAME_MESSAGE);
        this.name = name;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) {
        String taskType = task.getClass().getSimpleName().replace("Impl", "");

        if (tasks.contains(task)) {
            throw new InvalidUserInput(String.format(ALREADY_EXIST, taskType, task.getID(), getName()));
        }

        tasks.add(task);
        history.addEvent(String.format(TASK_ADDED, taskType, task.getID(), getName()));
    }

    @Override
    public void removeTask(Task task) {
        String taskType = task.getClass().getSimpleName().replace("Impl", "");

        if (!tasks.contains(task)) {
            throw new InvalidUserInput(
                    String.format(NOT_EXISTS, taskType, task.getID(), getName()));
        }

        tasks.remove(task);
        history.addEvent(String.format(TASK_REMOVED, taskType, task.getID(), getName()));
    }

    @Override
    public List<Event> getLog() {

        List<Event> tasksHistory = getTasks()
                .stream()
                .flatMap(task -> task.getLog().stream())
                .collect(Collectors.toList());

        List<Event> boardHistory = new ArrayList<>((history.getEvents()));
        boardHistory.addAll(tasksHistory);

        return boardHistory.stream().sorted(Comparator.comparing(Event::getOccurrence)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("Board name: %s - Board items: %d", getName(), getTasks().size());
    }
}
