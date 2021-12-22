package Task.Management.System.core.contracts;


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

    String addUser(String userName);

    User findUser(String userName);

    String addTeam(String teamName);

    Team findTeam(String teamName);

    String addBug(String teamName, String boardName,
                  String title, String description, List<String> stepsToReproduce,
                  Priority priority, Severity severity, String assignee);

    String addStory(String teamName, String boardName,
                    String title, String description,
                    Priority priority, Size size, String assignee);

    String addFeedback(String teamName, String boardName,
                       String title, String description, int rating);

}
