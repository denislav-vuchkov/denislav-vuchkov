package Task.Management.System.core.contracts;


import Task.Management.System.models.tasks.contracts.*;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.List;

public interface TaskManagementSystemRepository {

    List<Team> getTeams();

    List<User> getUsers();

    List<Task> getTasks();

    List<Bug> getBugs();

    List<Story> getStories();

    List<Feedback> getFeedbacks();

    List<AssignableTask> getAssignableTasks();

    String addTeam(String teamName);

    String addUser(String userName);

    Team findTeam(String teamName);

    User findUser(String userName);

    Board findBoard(String boardName, String teamName);

    String addBug(User user, String teamName, String boardName,
                  String title, String description, List<String> stepsToReproduce,
                  Priority priority, Severity severity, String assignee);

    String addFeedback(User user, String teamName, String boardName,
                       String title, String description, int rating);


    String addStory(User user, String teamName, String boardName,
                    String title, String description,
                    Priority priority, Size size, String assignee);

    Bug findBug(long bugID);

    Feedback findFeedback(long feedbackID);

    Story findStory(long taskID);

    Task findTask(long taskID);

    AssignableTask findAssignableTask(long assignableTaskID);

    void validateUserIsFromTeam(String userName, String teamName);

    void validateUserAndTaskFromSameTeam(String userName, long taskID);
}

