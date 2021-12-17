package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Task;

import java.util.List;

public interface Board extends Loggable, Nameable {

    List<Task> getTasks();

    void addTask(Task task);

    void removeTask(Task task);

}
