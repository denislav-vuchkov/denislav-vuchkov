package Task.Management.System.core;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    private final List<Team> teams;
    private final List<User> users;
    private final List<Task> tasks;

    public TaskManagementSystemRepositoryImpl() {
        teams = new ArrayList<>();
        users = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
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
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void addTeam(Team team) {
        teams.add(team);
    }
}
