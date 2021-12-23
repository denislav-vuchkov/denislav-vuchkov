package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Changeable;
import Task.Management.System.models.tasks.contracts.AssignableTask;

public interface User extends Changeable, Nameable, TaskHandler<AssignableTask> {

    void recordActivity(String description);

}
