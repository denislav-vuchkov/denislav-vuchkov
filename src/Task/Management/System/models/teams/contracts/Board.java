package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Loggable;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.subcontracts.Nameable;
import Task.Management.System.models.teams.contracts.subcontracts.TaskHandler;

public interface Board extends Loggable, Nameable, TaskHandler<Task> {

}
