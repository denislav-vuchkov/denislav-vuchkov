package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.enums.BugStatus;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Tasks;

import static Task.Management.System.models.contracts.ChangesLogger.*;

import java.util.List;

public class BugImpl extends AssignableTaskImpl implements Bug {

    public static final String STEPS_HEADER = "--STEPS TO REPRODUCE--";
    private BugStatus status;
    private final List<String> stepsToReproduce;
    private Priority priority;
    private Severity severity;
    private String assignee;

    public BugImpl(int id, String title, String description, List<String> stepsToReproduce,
                   Priority priority, Severity severity, String assignee) {
        super(id, Tasks.BUG, title, description, priority, assignee);
        this.status = BugStatus.ACTIVE;

        this.stepsToReproduce = stepsToReproduce;
        setSeverity(severity);
    }

    public BugImpl(int id, String title, String description, List<String> stepsToReproduce,
                   Priority priority, Severity severity) {
        this(id, title, description, stepsToReproduce, priority, severity, "Unassigned");
    }


    @Override
    public void setStatus(BugStatus status) {
        if (this.status == null) {
            this.status = status;
        } else if (!this.status.equals(status)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, status));
            this.status = status;
        }
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public void advanceStatus() {
        switch (status) {
            case ACTIVE:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, BugStatus.FIXED));
                status = BugStatus.FIXED;
                break;
            case FIXED:
                throw new IllegalArgumentException("Cannot advance status from fixed.");
        }
    }

    @Override
    public void retractStatus() {
        switch (status) {
            case ACTIVE:
                throw new IllegalArgumentException("Cannot advance status from fixed.");
            case FIXED:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", status, BugStatus.ACTIVE));
                status = BugStatus.ACTIVE;
                break;
        }
    }

    @Override
    public String getStepsToReproduce() {
        StringBuilder steps = new StringBuilder();
        steps.append(STEPS_HEADER).append("\n");

        for (int i = 1; i <= stepsToReproduce.size(); i++) {
            steps.append(stepsToReproduce.get(i)).append("\n");
        }

        steps.append(STEPS_HEADER).append("\n");

        return steps.toString();
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public void increaseSeverity() {
        switch (severity) {
            case MINOR:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Severity", severity, Severity.MAJOR));
                severity = Severity.MAJOR;
                break;
            case MAJOR:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Severity", severity, Severity.CRITICAL));
                severity = Severity.CRITICAL;
                break;
            case CRITICAL:
                throw new IllegalArgumentException("Cannot increase severity beyond Critical.");
        }
    }

    @Override
    public void decreaseSeverity() {
        switch (severity) {
            case MINOR:
                throw new IllegalArgumentException("Cannot decrease severity further than Minor.");
            case MAJOR:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Severity", severity, Severity.MINOR));
                severity = Severity.MINOR;
                break;
            case CRITICAL:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Severity", severity, Severity.MAJOR));
                severity = Severity.MAJOR;
                break;
        }
    }

    @Override
    public void setSeverity(Severity severity) {
        if (this.severity == null) {
            this.severity = severity;
        } else if (!this.severity.equals(severity)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Severity", this.severity, severity));
            this.severity = severity;
        } else {
            throw new IllegalArgumentException(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Severity", severity));
        }
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    @Override
    public String displayDetails() {
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
                super.displayDetails(),
                getPriority(), getSeverity(), getStatus(), getAssignee(),
                getStepsToReproduce(),
                displayComments(),
                getHistory());
    }
}
