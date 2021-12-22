package Task.Management.System.models.teams;

import Task.Management.System.models.contracts.Changeable;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamImpl implements Team {

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
        boards.add(board);
    }

    @Override
    public void removeBoard(Board board) {
        boards.remove(board);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public String getHistory() {
        StringBuilder history = new StringBuilder(String.format(TEAM_HISTORY_HEADER,
                this.getClass().getSimpleName().replace("Impl", ""), getName()))
                .append(System.lineSeparator());

        if (getUsers().isEmpty()) {
            history.append(TEAM_HISTORY_EMPTY).append(System.lineSeparator());
        } else {
            history.append(getUsers()
                    .stream()
                    .map(Changeable::getHistory)
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
