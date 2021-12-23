package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.contracts.subcontracts.Prioritizable;

public interface AssignableTask extends Task, Prioritizable {

    String getAssignee();

    void setAssignee(String assignee);

    void unAssign();

}
