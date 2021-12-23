package Task.Management.System.models.tasks.contracts.small_contracts;

import Task.Management.System.models.tasks.enums.Priority;

public interface Prioritisеable {

    Priority getPriority();

    void setPriority(Priority priority);

    void increasePriority();

    void decreasePriority();

}
