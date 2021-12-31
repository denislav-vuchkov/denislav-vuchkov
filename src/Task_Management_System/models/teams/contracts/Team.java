package Task_Management_System.models.teams.contracts;

import Task_Management_System.models.logger.contracts.Loggable;
import Task_Management_System.models.tasks.contracts.Task;
import Task_Management_System.models.teams.contracts.subcontracts.Nameable;

import java.util.List;

public interface Team extends Loggable, Nameable {

    List<Board> getBoards();

    void addBoard(Board board);

    void removeBoard(Board board);

    List<User> getUsers();

    void addUser(User user);

    void removeUser(User user);

    boolean containsTask(Task task);

}
