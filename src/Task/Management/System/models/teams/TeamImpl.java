package Task.Management.System.models.teams;

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
        ValidationHelpers.validateRange(name.length(), NAME_MIN_LEN, NAME_MAX_LEN, INVALID_NAME);
        this.name = name;
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {
        ValidationHelpers.entryNotAlreadyInList(board, getBoards(), getName());
        boards.add(board);
        history.addEvent(String.format(ADDITION, BOARD, board.getName()));
    }

    @Override
    public void removeBoard(Board board) {
        ValidationHelpers.entryExistInList(board, getBoards(), getName());
        boards.remove(board);
        history.addEvent(String.format(REMOVAL, BOARD, board.getName()));
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void addUser(User user) {
        ValidationHelpers.entryNotAlreadyInList(user, getUsers(), getName());
        users.add(user);
        history.addEvent(String.format(ADDITION, USER, user.getName()));
    }

    @Override
    public void removeUser(User user) {
        ValidationHelpers.entryExistInList(user, getUsers(), getName());
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
        return EventLogger.extract(history.getEvents(), getBoards(), getUsers());
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
