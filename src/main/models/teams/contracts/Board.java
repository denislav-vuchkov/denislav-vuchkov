package main.models.teams.contracts;

import main.models.logger.contracts.Loggable;
import main.models.tasks.contracts.Task;
import main.models.teams.contracts.subcontracts.Nameable;
import main.models.teams.contracts.subcontracts.TaskHandler;

public interface Board extends Loggable, Nameable, TaskHandler<Task> {

}
