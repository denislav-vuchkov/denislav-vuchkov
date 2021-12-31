package Task_Management_System.models.tasks.contracts;

import Task_Management_System.models.tasks.contracts.subcontracts.Prioritizable;

public interface AssignableTask extends Task, Prioritizable {

    String getAssignee();

    void setAssignee(String assignee);

    void unAssign();

}
