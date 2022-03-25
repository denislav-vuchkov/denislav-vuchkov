package main.models.teams.contracts;

import main.models.logger.contracts.Loggable;
import main.models.tasks.contracts.AssignableTask;
import main.models.teams.contracts.subcontracts.Nameable;
import main.models.teams.contracts.subcontracts.TaskHandler;

public interface User extends Loggable, Nameable, TaskHandler<AssignableTask> {

    void log(String description, String teamName);

}
