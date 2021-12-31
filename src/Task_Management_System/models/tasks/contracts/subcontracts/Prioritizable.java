package Task_Management_System.models.tasks.contracts.subcontracts;

import Task_Management_System.models.tasks.enums.Priority;

public interface Prioritizable {

    Priority getPriority();

    void setPriority(Priority priority);

}
