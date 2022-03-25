package main.core;

import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidUserInput;
import main.models.tasks.BugImpl;
import main.models.tasks.FeedbackImpl;
import main.models.tasks.StoryImpl;
import main.models.tasks.contracts.*;
import main.models.tasks.enums.Priority;
import main.models.tasks.enums.Severity;
import main.models.tasks.enums.Size;
import main.models.teams.TeamImpl;
import main.models.teams.UserImpl;
import main.models.teams.contracts.Board;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.models.teams.contracts.subcontracts.Nameable;
import main.utils.ListHelpers;

import java.util.ArrayList;
import java.util.List;

import static main.commands.BaseCommand.*;

public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {

    public static final String NOT_EXIST = "The %s does not exist! Create a %s with this name first.";
    public static final String ADDED_SUCCESSFULLY = "Successfully created %s %s.";

    public static final String TASK_ADDED_TO_BOARD = "%s with ID %d successfully added to board %s in team %s.";
    public static final String USER_NOT_FROM_TEAM = "The user should be a member of the team!";
    public static final String USER_OR_TASK_NOT_FROM_TEAM = "User and task should be from the same team!";
    private final List<Team> teams;
    private final List<User> users;
    private final List<Bug> bugs;
    private final List<Feedback> feedbacks;
    private final List<Story> stories;
    private long nextTaskID = 1;

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
        teams.add(new TeamImpl(teamName));
        return String.format(ADDED_SUCCESSFULLY, TEAM, teamName);
    }

    @Override
    public String addUser(String userName) {
        users.add(new UserImpl(userName));
        return String.format(ADDED_SUCCESSFULLY, USER, userName);
    }

    @Override
    public Team findTeam(String teamName) {
        return findByName(teamName, getTeams(), TEAM);
    }

    @Override
    public User findUser(String userName) {
        return findByName(userName, getUsers(), USER);
    }

    @Override
    public Board findBoard(String boardName, String teamName) {
        Team team = findByName(teamName, getTeams(), TEAM);
        return findByName(boardName, team.getBoards(), BOARD);
    }

    @Override
    public Team findTeam(Task task) {
        return teams.stream()
                .filter(team -> team.getBoards().stream().anyMatch(board -> board.getTasks().contains(task)))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(String.format(NOT_EXIST, TEAM, TEAM)));
    }

    @Override
    public String addBug(User creator, Team team, Board board, String title, String description,
                         List<String> stepsToReproduce, Priority priority, Severity severity, String assignee) {
        creator.log(String.format(CREATE, creator.getName(), "Bug", nextTaskID, board.getName()), team.getName());
        Bug bug = new BugImpl(nextTaskID, title, description, stepsToReproduce, priority, severity);
        board.addTask(bug);
        if (!assignee.isBlank()) findUser(assignee).addTask(bug);
        bugs.add(bug);
        return String.format(TASK_ADDED_TO_BOARD, "Bug", nextTaskID++, board.getName(), team.getName());
    }

    @Override
    public String addStory(User creator, Team team, Board board, String title, String description,
                           Priority priority, Size size, String assignee) {
        creator.log(String.format(CREATE, creator.getName(), "Story", nextTaskID, board.getName()), team.getName());
        Story story = new StoryImpl(nextTaskID, title, description, priority, size);
        board.addTask(story);
        if (!assignee.isBlank()) findUser(assignee).addTask(story);
        stories.add(story);
        return String.format(TASK_ADDED_TO_BOARD, "Story", nextTaskID++, board.getName(), team.getName());
    }

    @Override
    public String addFeedback(User creator, Team team, Board board, String title, String description, int rating) {
        creator.log(String.format(CREATE, creator.getName(), "Feedback", nextTaskID, board.getName()), team.getName());
        Feedback feedback = new FeedbackImpl(nextTaskID, title, description, rating);
        board.addTask(feedback);
        feedbacks.add(feedback);
        return String.format(TASK_ADDED_TO_BOARD, "Feedback", nextTaskID++, board.getName(), team.getName());
    }

    @Override
    public Bug findBug(long bugID) {
        return ListHelpers.findTask(bugID, getBugs());
    }

    @Override
    public Feedback findFeedback(long feedbackID) {
        return ListHelpers.findTask(feedbackID, getFeedbacks());
    }

    @Override
    public Story findStory(long storyID) {
        return ListHelpers.findTask(storyID, getStories());
    }

    @Override
    public Task findTask(long taskID) {
        return ListHelpers.findTask(taskID, getTasks());
    }

    @Override
    public AssignableTask findAssignableTask(long assignableTaskID) {
        return ListHelpers.findTask(assignableTaskID, getAssignableTasks());
    }

    private <T extends Nameable> T findByName(String name, List<T> elements, String type) {
        return elements
                .stream()
                .filter(element -> element.getName().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(String.format(NOT_EXIST, type, type)));
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
