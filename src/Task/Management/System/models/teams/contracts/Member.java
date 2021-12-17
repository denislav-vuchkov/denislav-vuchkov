package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contacts.Changeable;
import Task.Management.System.models.tasks.contracts.Task;

import java.util.List;

public interface Member extends Changeable, Nameable {

    List<Task> getAssignedTasks();

    void assignTask(Task task);

    void unassignTask(Task task);

}
