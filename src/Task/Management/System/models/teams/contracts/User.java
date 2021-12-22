package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Changeable;
import Task.Management.System.models.tasks.contracts.AssignableTask;

import java.util.List;

public interface User extends Changeable, Nameable {

    List<AssignableTask> getAssignedTasks();

    void recordActivity(String description);

    void assignTask(AssignableTask task);

    void unAssignTask(AssignableTask task);

}
