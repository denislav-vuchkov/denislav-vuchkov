package main.models.teams;

import main.models.logger.EventImpl;
import main.models.logger.LoggerImpl;
import main.models.logger.contracts.Logger;
import main.models.tasks.contracts.Task;
import main.models.teams.contracts.Board;
import main.utils.FormatHelpers;
import main.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static main.models.logger.contracts.Logger.*;

public class BoardImpl implements Board {

    private String name;
    private final List<Task> tasks;
    private final Logger history;

    public BoardImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new LoggerImpl();
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
    public List<EventImpl> getLog() {
        return FormatHelpers.combineLogs(history.getEvents(), getTasks());
    }

    @Override
    public String toString() {
        return String.format("Board name: %s - Board items: %d", getName(), getTasks().size());
    }
}
