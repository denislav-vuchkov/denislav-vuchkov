package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Changeable;
import Task.Management.System.models.tasks.contracts.Task;

import java.util.List;

public interface Board extends Changeable, Nameable {

    List<Task> getTasks();

    void addTask(Task task);

    void removeTask(Task task);

}
