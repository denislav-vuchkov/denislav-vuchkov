package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.Priority;

public interface Assignable extends Task {

    Priority getPriority();

    void increasePriority();

    void decreasePriority();

    void setPriority(Priority priority);

    String getAssignee();

    void setAssignee(String assignee);

    void unAssign();

}
