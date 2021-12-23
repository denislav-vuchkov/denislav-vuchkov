package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.contracts.small_contracts.Prioritisеable;

public interface AssignableTask extends Task, Prioritisеable {

    String getAssignee();

    void setAssignee(String assignee);

    void unAssign();

}
