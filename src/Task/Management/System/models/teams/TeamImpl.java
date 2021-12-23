package Task.Management.System.models.teams;

import Task.Management.System.models.contracts.Loggable;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamImpl implements Team {

    public static final String NOT_IN_TEAM = "%s %s is not in team %s";
    public static final String ALREADY_IN_TEAM = "%s %s is already in team %s";

    private final List<Board> boards;
    private final List<User> users;
    private String name;

    public TeamImpl(String name) {
        setName(name);
        boards = new ArrayList<>();
        users = new ArrayList<>();
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
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {
        if (getBoards().contains(board)) {
            throw new InvalidUserInput(
                    String.format(ALREADY_IN_TEAM,
                            board.getClass().getSimpleName().replace("Impl", " "),
                            board.getName(),
                            getName()));
        }

        boards.add(board);
    }

    @Override
    public void removeBoard(Board board) {
        if (!getBoards().contains(board)) {
            throw new InvalidUserInput(
                    String.format(NOT_IN_TEAM,
                            board.getClass().getSimpleName().replace("Impl", " "),
                            board.getName(),
                            getName()));
        }

        boards.remove(board);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void addUser(User user) {
        if (getUsers().contains(user)) {
            throw new InvalidUserInput(
                    String.format(ALREADY_IN_TEAM,
                            user.getClass().getSimpleName().replace("Impl", " "),
                            user.getName(),
                            getName()));
        }

        users.add(user);
    }

    @Override
    public void removeUser(User user) {
        if (!getUsers().contains(user)) {
            throw new InvalidUserInput(
                    String.format(NOT_IN_TEAM,
                            user.getClass().getSimpleName().replace("Impl", " "),
                            user.getName(),
                            getName()));
        }

        users.remove(user);
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
    public String getLog() {

        StringBuilder history = new StringBuilder(String.format(TEAM_HISTORY_HEADER,
                this.getClass().getSimpleName().replace("Impl", ""), getName()))
                .append(System.lineSeparator());

        if (getUsers().isEmpty()) {
            history.append(TEAM_HISTORY_EMPTY).append(System.lineSeparator());
        } else {
            history.append(getUsers()
                    .stream()
                    .map(Loggable::getLog)
                    .collect(Collectors.joining(System.lineSeparator())));
        }
        return history.toString().trim();
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
