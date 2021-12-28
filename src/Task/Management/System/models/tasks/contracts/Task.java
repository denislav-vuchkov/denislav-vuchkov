package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.contracts.Loggable;
import Task.Management.System.models.tasks.contracts.subcontracts.Commentable;
import Task.Management.System.models.tasks.contracts.subcontracts.Identifiable;
import Task.Management.System.models.tasks.contracts.subcontracts.Printable;

public interface Task extends Commentable, Identifiable, Loggable, Printable {

    String getTitle();

    String getDescription();

    String getStatus();

    void setStatus(TaskStatus status);

}
