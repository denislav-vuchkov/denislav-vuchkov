package Task.Management.System.core;


import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Member;
import Task.Management.System.models.teams.contracts.Team;

import java.util.ArrayList;
import java.util.List;


public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    private final List<Team> teams;
    private final List<Board> boards;
    private final List<Member> members;
    private final List<Task> tasks;

    public TaskManagementSystemRepositoryImpl() {
        teams = new ArrayList<>();
        boards = new ArrayList<>();
        members = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addNewMember(Member member) {
        if (getMembers().contains(member)) {
            throw new IllegalArgumentException(MEMBER_ALREADY_EXIST);
        }
        members.add(member);
    }
}
