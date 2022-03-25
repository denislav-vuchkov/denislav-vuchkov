package main.models.tasks.contracts;

import main.models.logger.contracts.Loggable;
import main.models.tasks.contracts.subcontracts.Commentable;
import main.models.tasks.contracts.subcontracts.Identifiable;
import main.models.tasks.contracts.subcontracts.Printable;

public interface Task extends Commentable, Identifiable, Loggable, Printable {

    String getTitle();

    String getDescription();

    TaskStatus getStatus();

    void setStatus(TaskStatus status);

}
