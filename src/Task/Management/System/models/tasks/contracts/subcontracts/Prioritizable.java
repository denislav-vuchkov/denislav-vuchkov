package Task.Management.System.models.tasks.contracts.subcontracts;

import Task.Management.System.models.tasks.enums.Priority;

public interface Prioritizable {

    Priority getPriority();

    void setPriority(Priority priority);

}
