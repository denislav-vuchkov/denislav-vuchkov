package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Severity;

import java.util.List;

public interface Bug extends AssignableTask {

    List<String> getStepsToReproduce();

    Severity getSeverity();

    void setSeverity(Severity severity);

    void setStatus(BugStatus status);

}
