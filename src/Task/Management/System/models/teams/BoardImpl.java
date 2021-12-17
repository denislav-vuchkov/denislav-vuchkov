package Task.Management.System.models.teams;

import Task.Management.System.models.tasks.ChangesLoggerImpl;
import Task.Management.System.models.tasks.contracts.ChangesLogger;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.tasks.contracts.ChangesLogger.CREATION_MESSAGE;

public class BoardImpl implements Board {

    private final ChangesLogger historyOfChanges;
    private final List<Task> tasks;
    private String name;

    public BoardImpl(String name) {
        this.name = name;
        tasks = new ArrayList<>();
        historyOfChanges = new ChangesLoggerImpl();
        historyOfChanges.addChange(String.format(
                CREATION_MESSAGE, getClass().getSimpleName().replace("Impl", " "), getName()));
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
        historyOfChanges.addChange(String.format(
                BOARD_TASK_ADDED,
                getClass().getSimpleName().replace("Impl", ""),
                getName(),
                task.getClass().getSimpleName().replace("Impl", ""),
                task.getTitle()));
        tasks.add(task);
    }

    @Override
    public void removeTask(Task task) {
        historyOfChanges.addChange(String.format(
                BOARD_TASK_REMOVED,
                getClass().getSimpleName().replace("Impl", ""),
                getName(),
                task.getClass().getSimpleName().replace("Impl", ""),
                task.getTitle()));
        tasks.remove(task);
    }

    @Override
    public String getHistory() {
        return historyOfChanges.getCompleteHistory();
    }
}
