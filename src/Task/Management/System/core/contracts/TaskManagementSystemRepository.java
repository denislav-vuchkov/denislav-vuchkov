package Task.Management.System.core.contracts;


import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.List;

public interface TaskManagementSystemRepository {

    List<Team> getTeams();

    List<Board> getBoards();

    List<User> getUsers();

    List<Task> getTasks();

    void addUser(User user);

    void addTeam(Team team);
    
}
