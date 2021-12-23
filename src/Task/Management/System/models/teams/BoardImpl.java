package Task.Management.System.models.teams;

import Task.Management.System.models.EventLoggerImpl;
import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.EventLogger.CREATION_MESSAGE;

public class BoardImpl implements Board {

    public static final String BOARD_TASK_ADDED = "%s with ID %d added in board %s.";
    public static final String BOARD_TASK_REMOVED = "%s with ID %d removed from board %s.";

    public static final String TASK_ALREADY_EXIST_IN_BOARD = "%s with %d already exists in board %s";
    public static final String TASK_DOES_NOT_EXIST = "%s with %d does not exist in board %s";

    private final EventLogger historyOfChanges;
    private final List<Task> tasks;
    private String name;

    public BoardImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        historyOfChanges = new EventLoggerImpl();
        historyOfChanges.addEvent(
                String.format(CREATION_MESSAGE,
                        getClass().getSimpleName().replace("Impl", ""),
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
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) {
        if (tasks.contains(task)) {
            throw new InvalidUserInput(
                    String.format(TASK_ALREADY_EXIST_IN_BOARD,
                            task.getClass().getSimpleName().replace("Impl", ""),
                            task.getID(),
                            getName()));
        }

        historyOfChanges.addEvent(
                String.format(BOARD_TASK_ADDED,
                        task.getClass().getSimpleName().replace("Impl", ""),
                        task.getID(),
                        getName()));

        tasks.add(task);
    }

    @Override
    public void removeTask(Task task) {
        if (!tasks.contains(task)) {
            throw new InvalidUserInput(
                    String.format(TASK_DOES_NOT_EXIST,
                            task.getClass().getSimpleName().replace("Impl", ""),
                            task.getID(),
                            getName()));
        }

        historyOfChanges.addEvent(
                String.format(BOARD_TASK_REMOVED,
                        task.getClass().getSimpleName().replace("Impl", ""),
                        task.getID(),
                        getName()));
        tasks.remove(task);
    }

    @Override
    public String getLog() {
        return historyOfChanges.getEvents();
    }

    @Override
    public String toString() {
        return String.format("Board name: %s - Board items: %d", getName(), getTasks().size());
    }
}
