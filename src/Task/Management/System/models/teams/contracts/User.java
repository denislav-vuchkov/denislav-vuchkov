package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.logger.contracts.Loggable;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.teams.contracts.subcontracts.Nameable;
import Task.Management.System.models.teams.contracts.subcontracts.TaskHandler;

public interface User extends Loggable, Nameable, TaskHandler<AssignableTask> {

    void log(String description);

}
