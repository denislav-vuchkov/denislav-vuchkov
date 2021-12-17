package Task.Management.System.models.contracts;

import Task.Management.System.models.enums.Priority;
import Task.Management.System.models.enums.Severity;

public interface Story extends Task {

    Priority getPriority();

    Severity getSeverity();

    String getAssignee();

}
