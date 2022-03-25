package main.models.teams;

import main.models.logger.EventImpl;
import main.models.logger.LoggerImpl;
import main.models.logger.contracts.Event;
import main.models.logger.contracts.Logger;
import main.models.tasks.contracts.AssignableTask;
import main.models.teams.contracts.User;
import main.utils.FormatHelpers;
import main.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static main.models.logger.contracts.Logger.*;

public class UserImpl implements User {

    public static final String TEAM_NOT_APPLICABLE = "N/A";

    private String name;
    private final List<AssignableTask> tasks;
    private final Logger history;

    public UserImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new LoggerImpl();
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

    @Override
    public void log(String description, String teamName) {
        history.addEvent(description, teamName);
    }

    @Override
    public List<EventImpl> getLog() {
        return history.getEvents()
                .stream()
                .sorted(Comparator.comparing(Event::getOccurrence))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("User: %s - Tasks: %d", getName(), getTasks().size());
    }


}
