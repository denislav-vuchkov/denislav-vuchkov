package Task.Management.System.models.teams;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.Event;
import Task.Management.System.models.EventLoggerImpl;
import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.contracts.Loggable;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.EventLogger.*;

public class TeamImpl implements Loggable, Team {

    public static final String NOT_IN_TEAM = "%s %s is not in team %s";
    public static final String ALREADY_IN_TEAM = "%s %s is already in team %s";
    private final List<Board> boards;
    private final List<User> users;
    private final EventLogger history;
    private String name;

    public TeamImpl(String name) {
        setName(name);
        boards = new ArrayList<>();
        users = new ArrayList<>();
        history = new EventLoggerImpl();
        history.addEvent(String.format(CREATION, TEAM, getName()));
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
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {
        if (getBoards().contains(board)) {
            throw new InvalidUserInput(String.format(ALREADY_IN_TEAM, BOARD, board.getName(), getName()));
        }
        boards.add(board);
        history.addEvent(String.format(ADDITION, BOARD, board.getName()));
    }

    @Override
    public void removeBoard(Board board) {
        if (!getBoards().contains(board)) {
            throw new InvalidUserInput(String.format(NOT_IN_TEAM, BOARD, board.getName(), getName()));
        }
        boards.remove(board);
        history.addEvent(String.format(REMOVAL, BOARD, board.getName()));
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void addUser(User user) {
        if (getUsers().contains(user)) {
            throw new InvalidUserInput(
                    String.format(ALREADY_IN_TEAM, USER, user.getName(), getName()));
        }
        users.add(user);
        history.addEvent(String.format(ADDITION, USER, user.getName()));
    }

    @Override
    public void removeUser(User user) {
        if (!getUsers().contains(user)) {
            throw new InvalidUserInput(
                    String.format(NOT_IN_TEAM, USER, user.getName(), getName()));
        }
        users.remove(user);
        history.addEvent(String.format(REMOVAL, USER, user.getName()));
    }

    @Override
    public boolean containsTask(Task task) {
        for (Board board : boards) {
            if (board.getTasks().contains(task)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Event> getLog() {
        return new ArrayList<>(history.getEvents());
    }

    @Override
    public String toString() {
        return String.format("Team: %s - Users: %d - Boards: %d - Tasks: %d",
                getName(),
                getUsers().size(),
                getBoards().size(),
                getBoards().stream().mapToInt(board -> board.getTasks().size()).sum());
    }
}
