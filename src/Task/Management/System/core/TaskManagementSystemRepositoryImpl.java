package Task.Management.System.core;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Nameable;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    private static final String NOT_EXIST = "The %s does not exist! Create a %s with this name first.";
    public static final String TEAM_DOES_NOT_EXIST = String.format(NOT_EXIST, "team", "team");
    public static final String USER_DOES_NOT_EXIST = String.format(NOT_EXIST, "user", "user");

    private static final String ALREADY_EXISTS = "This %s name already exists! Please choose a unique %s name.";
    public static final String TEAM_ALREADY_EXISTS = String.format(ALREADY_EXISTS, "team", "team");
    public static final String USER_ALREADY_EXISTS = String.format(ALREADY_EXISTS, "user", "user");


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
    public User findUser(String userName) {
        return getUsers()
                .stream()
                .filter(user -> user.getName().equals(userName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(USER_DOES_NOT_EXIST));
    }

    public void validateUniqueUserName(String userName) {
        if (getUsers().stream().anyMatch(user -> user.getName().equals(userName))) {
            throw new InvalidUserInput(USER_ALREADY_EXISTS);
        }
    }

    @Override
    public void addTeam(Team team) {
        teams.add(team);
    }

    @Override
    public Team findTeam(String teamName) {
        return getTeams()
                .stream()
                .filter(t -> t.getName().equals(teamName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(TEAM_DOES_NOT_EXIST));
    }

    @Override
    public void validateUniqueTeamName(String teamName) {
        if (getTeams().stream().anyMatch(team -> team.getName().equals(teamName))) {
            throw new InvalidUserInput(TEAM_ALREADY_EXISTS);
        }
    }

}
