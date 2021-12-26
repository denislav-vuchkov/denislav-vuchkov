package Task.Management.System.core;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
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

import static Task.Management.System.commands.BaseCommand.INVALID_ID;

public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    public static final String NOT_EXIST = "The %s does not exist! Create a %s with this name first.";
    public static final String ALREADY_EXISTS = "This %s name already exists! Please choose a unique %s name.";
    public static final String ADDED_SUCCESSFULLY = "%s %s created successfully.";

    public static final String TASK_ADDED_TO_BOARD = "%s with ID %d successfully added to board %s in team %s.";
    public static final String USER_NOT_FROM_TEAM = "The user should be a member of the team!";
    public static final String USER_OR_TASK_NOT_FROM_TEAM = "User and task should be from the same team!";

    private static long nextTaskID = 1;
    private final List<Team> teams;
    private final List<User> users;
    private final List<Bug> bugs;
    private final List<Feedback> feedbacks;
    private final List<Story> stories;

    public TaskManagementSystemRepositoryImpl() {
        teams = new ArrayList<>();
        users = new ArrayList<>();
        bugs = new ArrayList<>();
        feedbacks = new ArrayList<>();
        stories = new ArrayList<>();
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
    public List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }

    @Override
    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public List<Story> getStories() {
        return new ArrayList<>(stories);
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
    public String addTeam(String teamName) {
        if (getTeams().stream().anyMatch(team -> team.getName().equals(teamName))) {
            throw new InvalidUserInput(String.format(ALREADY_EXISTS, "team", "team"));
        }
        Team team = new TeamImpl(teamName);
        teams.add(team);
        return String.format(ADDED_SUCCESSFULLY, "Team", team.getName());
    }

    @Override
    public String addUser(String userName) {
        if (getUsers().stream().anyMatch(user -> user.getName().equals(userName))) {
            throw new InvalidUserInput(String.format(ALREADY_EXISTS, "user", "user"));
        }
        User user = new UserImpl(userName);
        users.add(user);
        return String.format(ADDED_SUCCESSFULLY, "User", user.getName());
    }

    @Override
    public Team findTeam(String teamName) {
        return getTeams()
                .stream()
                .filter(team -> team.getName().equals(teamName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(String.format(NOT_EXIST, "team", "team")));
    }

    @Override
    public User findUser(String userName) {
        return getUsers()
                .stream()
                .filter(user -> user.getName().equals(userName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(String.format(NOT_EXIST, "user", "user")));
    }

    @Override
    public Board findBoard(String boardName, String teamName) {
        Team team = findTeam(teamName);
        return team.getBoards()
                .stream()
                .filter(board -> board.getName().equals(boardName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(String.format(NOT_EXIST, "board", "board")));
    }

    @Override
    public String addBug(
            String teamName, String boardName, String title, String description,
            List<String> stepsToReproduce, Priority priority, Severity severity, String assignee) {

        Bug bug = new BugImpl(nextTaskID, title, description, stepsToReproduce, priority, severity);

        if (!assignee.isEmpty()) {
            validateUserIsFromTeam(teamName, assignee);
            findUser(assignee).addTask(bug);
        }

        findBoard(boardName, teamName).addTask(bug);
        bugs.add(bug);

        return String.format(TASK_ADDED_TO_BOARD, "Bug", nextTaskID++, boardName, teamName);
    }

    @Override
    public String addFeedback(String teamName, String boardName, String title, String description, int rating) {

        Feedback feedback = new FeedbackImpl(nextTaskID, title, description, rating);

        findBoard(boardName, teamName).addTask(feedback);
        feedbacks.add(feedback);

        return String.format(TASK_ADDED_TO_BOARD, "Feedback", nextTaskID++, boardName, teamName);
    }

    @Override
    public String addStory(String teamName, String boardName, String title, String description,
                           Priority priority, Size size, String assignee) {

        Story story = new StoryImpl(nextTaskID, title, description, priority, size);

        if (!assignee.isEmpty()) {
            validateUserIsFromTeam(teamName, assignee);
            findUser(assignee).addTask(story);
        }

        findBoard(boardName, teamName).addTask(story);
        stories.add(story);

        return String.format(TASK_ADDED_TO_BOARD, "Story", nextTaskID++, boardName, teamName);
    }

    @Override
    public Bug findBug(long bugID) {
        return genericTaskFinder(bugID, getBugs());
    }

    @Override
    public Feedback findFeedback(long feedbackID) {
        return genericTaskFinder(feedbackID, getFeedbacks());
    }

    @Override
    public Story findStory(long storyID) {
        return genericTaskFinder(storyID, getStories());
    }

    @Override
    public Task findTask(long taskID) {
        return genericTaskFinder(taskID, getTasks());
    }

    @Override
    public AssignableTask findAssignableTask(long assignableTaskID) {
        return genericTaskFinder(assignableTaskID, getAssignableTasks());
    }

    private <T extends Task> T genericTaskFinder(long taskID, List<T> tasks) {
        return tasks.stream()
                .filter(task -> task.getID() == taskID)
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(INVALID_ID));
    }

    @Override
    public void validateUserIsFromTeam(String userName, String teamName) {
        User user = findUser(userName);
        Team team = findTeam(teamName);
        if (!team.getUsers().contains(user)) {
            throw new InvalidUserInput(USER_NOT_FROM_TEAM);
        }
    }

    @Override
    public void validateUserAndTaskFromSameTeam(String userName, long taskID) {
        User user = findUser(userName);
        Task task = findTask(taskID);
        for (Team team : teams) {
            if (team.getUsers().contains(user) && team.containsTask(task)) {
                return;
            }
        }
        throw new InvalidUserInput(USER_OR_TASK_NOT_FROM_TEAM);
    }
}
