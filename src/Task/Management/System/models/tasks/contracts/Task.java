package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.contracts.Loggable;
import Task.Management.System.models.tasks.contracts.subcontracts.Commentable;
import Task.Management.System.models.tasks.contracts.subcontracts.Identifiable;
import Task.Management.System.models.tasks.contracts.subcontracts.Printable;
import Task.Management.System.models.tasks.contracts.subcontracts.Titleable;

public interface Task extends Identifiable, Loggable, Printable, Commentable, Titleable {

    String getDescription();

    void setDescription(String description);

    String getStatus();

}
