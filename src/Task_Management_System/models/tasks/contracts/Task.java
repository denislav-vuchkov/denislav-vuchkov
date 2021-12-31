package Task_Management_System.models.tasks.contracts;

import Task_Management_System.models.logger.contracts.Loggable;
import Task_Management_System.models.tasks.contracts.subcontracts.Commentable;
import Task_Management_System.models.tasks.contracts.subcontracts.Identifiable;
import Task_Management_System.models.tasks.contracts.subcontracts.Printable;

public interface Task extends Commentable, Identifiable, Loggable, Printable {

    String getTitle();

    String getDescription();

    TaskStatus getStatus();

    void setStatus(TaskStatus status);

}
