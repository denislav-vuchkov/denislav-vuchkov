package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Task;

import java.util.List;

public interface Member extends Loggable, Nameable {

    List<Task> getAssignedTasks();

    void assignTask(Task task);

    void unassignTask(Task task);

}
