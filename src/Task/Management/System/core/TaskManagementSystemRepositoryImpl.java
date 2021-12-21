package Task.Management.System.core;


import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Nameable;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.ArrayList;
import java.util.List;


public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    private final List<Team> teams;
    private final List<Board> boards;
    private final List<User> users;
    private final List<Task> tasks;

    public TaskManagementSystemRepositoryImpl() {
        teams = new ArrayList<>();
        boards = new ArrayList<>();
        users = new ArrayList<>();
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
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addNewUser(User user) {
        if (containsEntry(getUsers(), user.getName())) {
            throw new IllegalArgumentException(USER_ALREADY_EXIST);
        }
        users.add(user);
    }

    @Override
    public void addNewTeam(Team team) {
        if (containsEntry(getTeams(), team.getName())) {
            throw new IllegalArgumentException(TEAM_ALREADY_EXIST);
        }
        teams.add(team);
    }

    @Override
    public <E extends Nameable> E findByName(List<E> list, String name) {
        return list
                .stream()
                .filter(entry -> entry.getName().equals(name))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public <E extends Nameable> boolean containsEntry(List<E> list, String name) {
        return list
                .stream()
                .anyMatch(element -> element.getName().equals(name));
    }


}
