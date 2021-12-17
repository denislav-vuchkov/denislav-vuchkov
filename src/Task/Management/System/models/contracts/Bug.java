package Task.Management.System.models.contracts;

import Task.Management.System.models.enums.BugStatus;
import Task.Management.System.models.enums.Priority;
import Task.Management.System.models.enums.Severity;

public interface Bug extends Task {

    String getStepsToReproduce();

    void setStatus(BugStatus status);

    Priority getPriority();

    void increasePriority();

    void decreasePriority();

    void setPriority(Priority priority);

    Severity getSeverity();

    void increaseSeverity();

    void decreaseSeverity();

    void setSeverity(Severity severity);

    String getAssignee();

    void setAssignee(String assignee);

}
