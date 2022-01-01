package Task_Management_System.core;

import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.BugImpl;
import Task_Management_System.models.tasks.FeedbackImpl;
import Task_Management_System.models.tasks.StoryImpl;
import Task_Management_System.models.tasks.contracts.*;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Severity;
import Task_Management_System.models.tasks.enums.Size;
import Task_Management_System.models.teams.TeamImpl;
import Task_Management_System.models.teams.UserImpl;
import Task_Management_System.models.teams.contracts.Board;
import Task_Management_System.models.teams.contracts.Team;
import Task_Management_System.models.teams.contracts.User;
import Task_Management_System.models.teams.contracts.subcontracts.Nameable;
import Task_Management_System.utils.ListHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task_Management_System.commands.BaseCommand.*;

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
        ValidationHelpers.validateUniqueName(teamName, getTeams(), TEAM);
        teams.add(new TeamImpl(teamName));
        return String.format(ADDED_SUCCESSFULLY, TEAM, teamName);
    }

    @Override
    public String addUser(String userName) {
        ValidationHelpers.validateUniqueName(userName, getUsers(), USER);
        users.add(new UserImpl(userName));
        return String.format(ADDED_SUCCESSFULLY, USER, userName);
    }

    @Override
    public <T extends Nameable> T findByName(List<T> collection, String targetName, String typeOfCollection) {
        return collection
                .stream()
                .filter(element -> element.getName().equalsIgnoreCase(targetName))
                .findAny()
                .orElseThrow(() -> new InvalidUserInput(String.format(NOT_EXIST, typeOfCollection, typeOfCollection)));
    }

    @Override
    public String addBug(User creator, String teamName, String boardName, String title, String description,
                         List<String> stepsToReproduce, Priority priority, Severity severity, String assignee) {
        if (!assignee.isBlank()) validateUserIsFromTeam(assignee, teamName);
        Team team = findByName(teams, teamName, TEAM);
        Board board = findByName(team.getBoards(), boardName, BOARD);

        creator.log(String.format(USER_CREATED_TASK, creator.getName(), "Bug", nextTaskID, boardName));
        Bug bug = new BugImpl(nextTaskID, title, description, stepsToReproduce, priority, severity);
        board.addTask(bug);
        if (!assignee.isBlank()) findByName(users, assignee, USER).addTask(bug);

        bugs.add(bug);
        return String.format(TASK_ADDED_TO_BOARD, "Bug", nextTaskID++, boardName, teamName);
    }

    @Override
    public String addStory(User creator, String teamName, String boardName, String title, String description,
                           Priority priority, Size size, String assignee) {
        if (!assignee.isBlank()) validateUserIsFromTeam(assignee, teamName);
        Team team = findByName(teams, teamName, TEAM);
        Board board = findByName(team.getBoards(), boardName, BOARD);

        creator.log(String.format(USER_CREATED_TASK, creator.getName(), "Story", nextTaskID, boardName));
        Story story = new StoryImpl(nextTaskID, title, description, priority, size);
        board.addTask(story);
        if (!assignee.isBlank()) findByName(users, assignee, USER).addTask(story);

        stories.add(story);
        return String.format(TASK_ADDED_TO_BOARD, "Story", nextTaskID++, boardName, teamName);
    }

    @Override
    public String addFeedback(User creator, String teamName, String boardName, String title, String description, int rating) {
        Team team = findByName(teams, teamName, TEAM);
        Board board = findByName(team.getBoards(), boardName, BOARD);

        creator.log(String.format(USER_CREATED_TASK, creator.getName(), "Feedback", nextTaskID, boardName));
        Feedback feedback = new FeedbackImpl(nextTaskID, title, description, rating);
        board.addTask(feedback);

        feedbacks.add(feedback);
        return String.format(TASK_ADDED_TO_BOARD, "Feedback", nextTaskID++, boardName, teamName);
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

    @Override
    public void validateUserIsFromTeam(String userName, String teamName) {
        User user = findByName(users, userName, USER);
        Team team = findByName(teams, teamName, TEAM);
        if (!team.getUsers().contains(user)) {
            throw new InvalidUserInput(USER_NOT_FROM_TEAM);
        }
    }

    @Override
    public void validateUserAndTaskFromSameTeam(String userName, long taskID) {
        User user = findByName(users, userName, USER);
        Task task = findTask(taskID);
        for (Team team : teams) {
            if (team.getUsers().contains(user) && team.containsTask(task)) {
                return;
            }
        }
        throw new InvalidUserInput(USER_OR_TASK_NOT_FROM_TEAM);
    }
}
