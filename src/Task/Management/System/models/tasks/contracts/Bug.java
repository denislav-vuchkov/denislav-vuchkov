package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Severity;

public interface Bug extends BugsAndStoryIntermediate {

    String getStepsToReproduce();

    void setStatus(BugStatus status);

    Severity getSeverity();

    void increaseSeverity();

    void decreaseSeverity();

    void setSeverity(Severity severity);


}
