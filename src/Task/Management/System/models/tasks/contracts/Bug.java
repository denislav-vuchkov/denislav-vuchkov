package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Severity;

public interface Bug extends AssignableTask {

    String getStepsToReproduce();

    Severity getSeverity();

    void setSeverity(Severity severity);

    void increaseSeverity();

    void decreaseSeverity();

    void setStatus(BugStatus status);

}
