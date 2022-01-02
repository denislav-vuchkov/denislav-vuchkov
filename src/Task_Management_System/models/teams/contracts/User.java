package Task_Management_System.models.teams.contracts;

import Task_Management_System.models.logger.contracts.Loggable;
import Task_Management_System.models.tasks.contracts.AssignableTask;
import Task_Management_System.models.teams.contracts.subcontracts.Nameable;
import Task_Management_System.models.teams.contracts.subcontracts.TaskHandler;

public interface User extends Loggable, Nameable, TaskHandler<AssignableTask> {

    void log(String description, String teamName);

}
