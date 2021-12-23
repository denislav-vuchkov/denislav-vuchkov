package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.contracts.Changeable;
import Task.Management.System.models.tasks.contracts.small_contracts.Commentable;
import Task.Management.System.models.tasks.contracts.small_contracts.Identifiable;
import Task.Management.System.models.tasks.contracts.small_contracts.Printable;
import Task.Management.System.models.tasks.contracts.small_contracts.Titleable;

import java.util.List;

public interface Task extends Identifiable, Changeable, Printable, Commentable, Titleable {

    String getDescription();

    void setDescription(String description);

    String getStatus();

    void advanceStatus();

    void retractStatus();

}
