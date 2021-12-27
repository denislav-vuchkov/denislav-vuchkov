package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Tasks;

import java.util.List;

import static Task.Management.System.models.contracts.EventLogger.CHANGE_MESSAGE;
import static Task.Management.System.models.contracts.EventLogger.IMPOSSIBLE_CHANGE_MESSAGE;

public class BugImpl extends AssignableTaskImpl implements Bug {

    public static final String STEPS_HEADER = "--STEPS TO REPRODUCE--";
    public static final String LOWER_BOUNDARY = "Cannot decrease %s further than %s.";
    public static final String UPPER_BOUNDARY = "Cannot increase %s beyond %s.";
    private final List<String> stepsToReproduce;
    private BugStatus status;
    private Severity severity;

    public BugImpl(long id, String title, String description, List<String> stepsToReproduce,
                   Priority priority, Severity severity) {
        super(id, Tasks.BUG, title, description, priority);
        this.stepsToReproduce = stepsToReproduce;
        setSeverity(severity);
        setStatus(BugStatus.ACTIVE);
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public void setStatus(BugStatus status) {
        if (this.status == null) {
            this.status = status;
            return;
        }
        if (this.status.equals(status)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Status", getStatus()));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, status));
        this.status = status;
    }

    @Override
    public String getStepsToReproduce() {
        StringBuilder steps = new StringBuilder();
        int[] index = new int[1];

        steps.append(STEPS_HEADER).append("\n");
        stepsToReproduce.forEach(step -> steps.append(String.format("%d. %s%n", ++index[0], step)));
        steps.append(STEPS_HEADER);

        return steps.toString();
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(Severity severity) {
        if (this.severity == null) {
            this.severity = severity;
            return;
        }
        if (this.severity.equals(severity)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Severity", severity));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Severity", this.severity, severity));
        this.severity = severity;
    }

    @Override
    public String printDetails() {
        return String.format("Task type: %s%n" +
                        "%s" +
                        "Priority: %s%n" +
                        "Severity: %s%n" +
                        "Status: %s%n" +
                        "Assignee: %s%n" +
                        "%s" +
                        "%s" +
                        "%s",
                this.getClass().getSimpleName().replace("Impl", ""),
                super.printDetails(),
                getPriority(), getSeverity(), getStatus(), getAssignee(),
                getStepsToReproduce(),
                printComments(),
                getLog());
    }

    @Override
    public String toString() {
        return String.format("%s - ID: %d - Title: %s - Priority: %s - Severity: %s - " +
                        "Status - %s - Assignee - %s - Comments: %d",
                this.getClass().getSimpleName().replace("Impl", ""),
                getID(), getTitle(), getPriority(), getSeverity(),
                getStatus(), getAssignee(), getComments().size());
    }
}
