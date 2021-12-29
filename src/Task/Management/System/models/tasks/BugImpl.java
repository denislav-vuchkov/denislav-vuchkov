package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.FormatHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Task.Management.System.models.logger.contracts.Logger.TASK_CHANGE;

public class BugImpl extends AssignableTaskImpl implements Bug {

    public static final String SEVERITY_FIELD = "Severity";
    private final List<String> stepsToReproduce;
    private Severity severity;

    public BugImpl(long id, String title, String description, List<String> stepsToReproduce,
                   Priority priority, Severity severity) {
        super(id, Tasks.BUG, title, description, priority, BugStatus.ACTIVE);
        this.stepsToReproduce = stepsToReproduce;
        setSeverity(severity);
    }

    @Override
    public List<String> getStepsToReproduce() {
        return new ArrayList<>(stepsToReproduce);
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
        checkForDuplication(getSeverity(), severity, SEVERITY_FIELD);
        logActivity(String.format(TASK_CHANGE, Tasks.BUG, getID(), SEVERITY_FIELD, this.severity, severity));
        this.severity = severity;
    }

    @Override
    public String toString() {
        return String.format("%s ID: %d - Title: %s - Steps: %d - " +
                        "Priority: %s - Severity: %s - Status: %s - Assignee: %s - Comments: %d",
                FormatHelpers.getType(this), getID(), getTitle(), getStepsToReproduce().size(),
                getPriority(), getSeverity(), getStatus(), getAssignee(), getComments().size());
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
                FormatHelpers.getType(this),
                super.printDetails(),
                getPriority(), getSeverity(), getStatus(), getAssignee(),
                getStepsToReproduce(),
                getComments().stream().map(Comment::toString).collect(Collectors.joining(System.lineSeparator())),
                getLog());
    }
}
