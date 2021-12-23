package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.contracts.Changeable;
import Task.Management.System.models.tasks.contracts.Task;

public interface Board extends Changeable, Nameable, TaskHandler<Task> {

}
