package Task.Management.System.models.contracts;

import Task.Management.System.models.enums.Priority;
import Task.Management.System.models.enums.Severity;

public interface Bug extends Task {

    String getStepsToReproduce();

    Priority getPriority();

    Severity getSeverity();

    String getAssignee();

}
