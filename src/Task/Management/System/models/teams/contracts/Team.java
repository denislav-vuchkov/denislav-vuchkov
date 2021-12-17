package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Changeable;

import java.util.List;

public interface Team extends Changeable,Nameable {

    List<Board> getBoards();

    void addBoard(Board board);

    void removeBoard(Board board);

    List<User> getUsers();

    void addUser(User user);

    void removeUser(User user);

}
