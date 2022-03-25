package main.models.tasks.contracts.subcontracts;

import main.models.tasks.enums.Priority;

public interface Prioritizable {

    Priority getPriority();

    void setPriority(Priority priority);

}
