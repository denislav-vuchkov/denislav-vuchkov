package main.models.tasks.contracts;

import main.models.tasks.contracts.subcontracts.Prioritizable;

public interface AssignableTask extends Task, Prioritizable {

    String getAssignee();

    void setAssignee(String assignee);

    void unAssign();

}
