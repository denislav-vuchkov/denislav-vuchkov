package Task.Management.System.core.contracts;


import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.List;

public interface TaskManagementSystemRepository {

    List<Team> getTeams();

    List<User> getUsers();

    List<Task> getTasks();

    void addUser(User user);

    User findUser(String userName);

    void validateUniqueUserName(String userName);

    void addTeam(Team team);

    Team findTeam(String teamName);

    void validateUniqueTeamName(String teamName);

    void addBug(String title, String description, List<String> stepsToReproduce, Priority priority,
                Severity severity, String assignee);

    void addStory(String title, String description, Priority priority, Size size, String assignee);

    void addFeedback(String title, String description, int rating);

}
