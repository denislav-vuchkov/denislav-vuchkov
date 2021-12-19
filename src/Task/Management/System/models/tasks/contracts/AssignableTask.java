package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.Priority;

public interface AssignableTask extends Task {

    Priority getPriority();

    void setPriority(Priority priority);

    void increasePriority();

    void decreasePriority();

    String getAssignee();

    void setAssignee(String assignee);

    void unAssign();

}
