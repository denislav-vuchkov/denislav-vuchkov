package Task.Management.System.core.contracts;


import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Nameable;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import java.util.List;

public interface TaskManagementSystemRepository {

    String TEAM_ALREADY_EXIST = "The team name already exists!";
    String USER_ALREADY_EXIST = "This user name already exists!";

    List<Team> getTeams();

    List<Board> getBoards();

    List<User> getUsers();

    List<Task> getTasks();

    void addNewTeam(Team team);

    void addNewUser(User user);

    <E extends Nameable> E findByName(List<E> list, String name);

    <E extends Nameable> boolean containsEntry(List<E> list, String name);

}
