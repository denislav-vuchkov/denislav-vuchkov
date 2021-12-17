package Task.Management.System.models.teams;

import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Loggable;
import Task.Management.System.models.teams.contracts.Member;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamImpl implements Team {

    private final List<Board> boards;
    private final List<Member> members;
    private String name;

    public TeamImpl(String name) {
        this.name = name;
        boards = new ArrayList<>();
        members = new ArrayList<>();
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
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(Member member) {
        members.add(member);
    }

    @Override
    public void removeMember(Member member) {
        members.add(member);
    }

    @Override
    public String getHistory() {
        StringBuilder history = new StringBuilder(TEAM_HISTORY_HEADER).append(System.lineSeparator());
        if (getMembers().isEmpty()) {
            history.append(TEAM_HISTORY_EMPTY).append(System.lineSeparator());
        } else {
            history.append(getMembers()
                    .stream()
                    .map(Loggable::getHistory)
                    .collect(Collectors.joining(System.lineSeparator())));
        }
        return history.toString().trim();
    }
}
