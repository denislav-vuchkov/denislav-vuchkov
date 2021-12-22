package Task.Management.System.core;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.BugImpl;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.tasks.StoryImpl;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    public static final String TASK_ADDED_TO_BOARD =
            "%s with ID %d successfully added to board %s in team %s.";
    private static final String NOT_EXIST = "The %s does not exist! Create a %s with this name first.";
    public static final String TEAM_DOES_NOT_EXIST = String.format(NOT_EXIST, "team", "team");
    public static final String USER_DOES_NOT_EXIST = String.format(NOT_EXIST, "user", "user");
    public static final String BOARD_DOES_NOT_EXIST = String.format(NOT_EXIST, "board", "board");

    private static final String ALREADY_EXISTS = "This %s name already exists! Please choose a unique %s name.";
    public static final String TEAM_ALREADY_EXISTS = String.format(ALREADY_EXISTS, "team", "team");
    public static final String USER_ALREADY_EXISTS = String.format(ALREADY_EXISTS, "user", "user");

    private static int nextTaskID = 0;
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
    public void validateUniqueTeamName(String teamName) {
        if (getTeams().stream().anyMatch(team -> team.getName().equals(teamName))) {
            throw new InvalidUserInput(TEAM_ALREADY_EXISTS);
        }
    }

    @Override
    public String addBug(String teamName, String boardName, String title, String description, List<String> stepsToReproduce,
                       Priority priority, Severity severity, String assignee) {
        Task bug = new BugImpl(++nextTaskID, title, description, stepsToReproduce, priority, severity, assignee);
        tasks.add(bug);

        addTaskToBoard(bug, boardName, teamName);

        return String.format(TASK_ADDED_TO_BOARD, "Bug", nextTaskID, boardName, teamName);
    }

    @Override
    public String addStory(String teamName, String boardName, String title, String description,
                         Priority priority, Size size, String assignee) {
        Task story = new StoryImpl(++nextTaskID, title, description, priority, size, assignee);
        tasks.add(story);

        addTaskToBoard(story, boardName, teamName);

        return String.format(TASK_ADDED_TO_BOARD, "Story", nextTaskID, boardName, teamName);
    }

    @Override
    public String addFeedback(String teamName, String boardName, String title, String description, int rating) {
        Task feedback = new FeedbackImpl(++nextTaskID, title, description, rating);
        tasks.add(feedback);

        addTaskToBoard(feedback, boardName, teamName);

        return String.format(TASK_ADDED_TO_BOARD, "Feedback", nextTaskID, boardName, teamName);
    }

    private void addTaskToBoard(Task task, String boardName, String teamName) {
        Board board = findBoard(boardName, teamName);
        board.addTask(task);
    }

    private Board findBoard(String boardName, String teamName) {
        Team team = findTeam(teamName);

        Board board = team.getBoards()
                .stream()
                .filter(e -> e.getName().equals(boardName))
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(BOARD_DOES_NOT_EXIST));

        return board;
    }

    @Override
    public Team findTeam(String teamName) {
        return getTeams()
                .stream()
                .filter(t -> t.getName().equals(teamName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(TEAM_DOES_NOT_EXIST));
    }
}
