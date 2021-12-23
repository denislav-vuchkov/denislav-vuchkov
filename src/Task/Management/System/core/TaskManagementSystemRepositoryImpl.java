package Task.Management.System.core;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.BugImpl;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.tasks.StoryImpl;
import Task.Management.System.models.tasks.contracts.*;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.teams.TeamImpl;
import Task.Management.System.models.teams.UserImpl;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.commands.CreateStoryCommand.UNASSIGNED;

public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    public static final String USER_ADDED_SUCCESSFULLY = "User %s created successfully.";
    public static final String TEAM_ADDED_SUCCESSFULLY = "Team %s created successfully.";

    public static final String TASK_ADDED_TO_BOARD =
            "%s with ID %d successfully added to board %s in team %s.";

    public static final String USER_NOT_IN_TEAM = "User %s does not exist in team %s.";
    public static final String INVALID_ID = "Invalid ID provided.";

    private static final String NOT_EXIST = "The %s does not exist! Create a %s with this name first.";
    public static final String TEAM_DOES_NOT_EXIST = String.format(NOT_EXIST, "team", "team");
    public static final String USER_DOES_NOT_EXIST = String.format(NOT_EXIST, "user", "user");
    public static final String BOARD_DOES_NOT_EXIST = String.format(NOT_EXIST, "board", "board");

    private static final String ALREADY_EXISTS = "This %s name already exists! Please choose a unique %s name.";
    public static final String TEAM_ALREADY_EXISTS = String.format(ALREADY_EXISTS, "team", "team");
    public static final String USER_ALREADY_EXISTS = String.format(ALREADY_EXISTS, "user", "user");

    private static long nextTaskID = 1;
    private final List<Team> teams;
    private final List<User> users;
    private final List<Bug> bugs;
    private final List<Story> stories;
    private final List<Feedback> feedbacks;

    public TaskManagementSystemRepositoryImpl() {
        teams = new ArrayList<>();
        users = new ArrayList<>();
        bugs = new ArrayList<>();
        stories = new ArrayList<>();
        feedbacks = new ArrayList<>();
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
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(bugs);
        tasks.addAll(stories);
        tasks.addAll(feedbacks);
        return new ArrayList<>(tasks);
    }

    @Override
    public List<AssignableTask> getAssignableTasks() {
        List<AssignableTask> assignableTasks = new ArrayList<>();
        assignableTasks.addAll(bugs);
        assignableTasks.addAll(stories);
        return new ArrayList<>(assignableTasks);
    }

    @Override
    public List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }

    @Override
    public List<Story> getStories() {
        return new ArrayList<>(stories);
    }

    @Override
    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public String addUser(String userName) {
        validateUniqueUserName(userName);
        User user = new UserImpl(userName);
        users.add(user);
        return String.format(USER_ADDED_SUCCESSFULLY, user.getName());
    }

    @Override
    public User findUser(String userName) {
        return getUsers()
                .stream()
                .filter(user -> user.getName().equals(userName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(USER_DOES_NOT_EXIST));
    }

    private void validateUniqueUserName(String userName) {
        if (getUsers().stream().anyMatch(user -> user.getName().equals(userName))) {
            throw new InvalidUserInput(USER_ALREADY_EXISTS);
        }
    }

    @Override
    public String addTeam(String teamName) {
        validateUniqueTeamName(teamName);
        Team team = new TeamImpl(teamName);
        teams.add(team);
        return String.format(TEAM_ADDED_SUCCESSFULLY, team.getName());
    }

    @Override
    public Team findTeam(String teamName) {
        return getTeams()
                .stream()
                .filter(team -> team.getName().equals(teamName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(TEAM_DOES_NOT_EXIST));
    }

    private void validateUniqueTeamName(String teamName) {
        if (getTeams().stream().anyMatch(team -> team.getName().equals(teamName))) {
            throw new InvalidUserInput(TEAM_ALREADY_EXISTS);
        }
    }

    @Override
    public String addBug(String teamName, String boardName, String title, String description,
                         List<String> stepsToReproduce, Priority priority, Severity severity, String assignee) {
        checkIfAssigneeIsValid(teamName, assignee);
        Bug bug = new BugImpl(nextTaskID, title, description, stepsToReproduce, priority, severity);

        addTaskToBoard(bug, boardName, teamName);
        if (!assignee.isEmpty()) {
            addTaskToUser(bug, assignee);
        }

        bugs.add(bug);
        nextTaskID++;

        return String.format(TASK_ADDED_TO_BOARD, "Bug", bug.getID(), boardName, teamName);
    }

    @Override
    public String addFeedback(String teamName, String boardName, String title, String description, int rating) {
        Feedback feedback = new FeedbackImpl(nextTaskID, title, description, rating);

        addTaskToBoard(feedback, boardName, teamName);
        feedbacks.add(feedback);
        nextTaskID++;

        return String.format(TASK_ADDED_TO_BOARD, "Feedback", feedback.getID(), boardName, teamName);
    }

    @Override
    public String addStory(String teamName, String boardName, String title, String description,
                           Priority priority, Size size, String assignee) {
        checkIfAssigneeIsValid(teamName, assignee);
        Story story = new StoryImpl(nextTaskID, title, description, priority, size);

        addTaskToBoard(story, boardName, teamName);
        if (!assignee.isEmpty()) {
            addTaskToUser(story, assignee);
        }

        stories.add(story);
        nextTaskID++;

        return String.format(TASK_ADDED_TO_BOARD, "Story", story.getID(), boardName, teamName);
    }

    private void addTaskToUser(AssignableTask task, String assignee) {
        User user = users.stream()
                .filter(u -> u.getName().equals(assignee))
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(USER_DOES_NOT_EXIST));

        user.assignTask(task);
    }

    private void checkIfAssigneeIsValid(String teamName, String assignee) {
        if (!assignee.equals(UNASSIGNED)) {
            findTeam(teamName).getUsers()
                    .stream()
                    .filter(user -> user.getName().equals(assignee))
                    .findFirst()
                    .orElseThrow(() -> new InvalidUserInput(String.format(USER_NOT_IN_TEAM, assignee, teamName)));
        }
    }

    private void addTaskToBoard(Task task, String boardName, String teamName) {
        Board board = findBoard(boardName, teamName);
        board.addTask(task);
    }

    public Board findBoard(String boardName, String teamName) {
        Team team = findTeam(teamName);
        return team.getBoards()
                .stream()
                .filter(board -> board.getName().equals(boardName))
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(BOARD_DOES_NOT_EXIST));
    }

    @Override
    public Task findTask(int taskID) {
        return genericTaskFinder(taskID, getTasks());
    }

    @Override
    public Bug findBug(int bugID) {
        return genericTaskFinder(bugID, getBugs());
    }

    @Override
    public Feedback findFeedback(int feedbackID) {
        return genericTaskFinder(feedbackID, getFeedbacks());
    }

    @Override
    public Story findStory(int storyID) {
        return genericTaskFinder(storyID, getStories());
    }

    private <T extends Task> T genericTaskFinder(int taskID, List<T> tasks) {
        return tasks.stream()
                .filter(task -> task.getID() == taskID)
                .findFirst()
                .orElseThrow(() -> new InvalidUserInput(INVALID_ID));
    }
}