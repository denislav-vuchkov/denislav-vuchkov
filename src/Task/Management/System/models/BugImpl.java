package Task.Management.System.models;

import Task.Management.System.models.contracts.Bug;
import Task.Management.System.models.enums.BugStatus;
import Task.Management.System.models.enums.Priority;
import Task.Management.System.models.enums.Severity;

import static Task.Management.System.models.contracts.ChangesLogger.*;

import java.util.List;

public class BugImpl extends TaskBase implements Bug {

    private BugStatus status;
    private final List<String> stepsToReproduce;
    private Priority priority;
    private Severity severity;
    private String assignee;

    public BugImpl(int id, String title, String description, List<String> stepsToReproduce,
                   Priority priority, Severity severity, String assignee) {
        super(id, title, description);
        this.status = BugStatus.ACTIVE;

        this.stepsToReproduce = stepsToReproduce;
        setPriority(priority);
        setSeverity(severity);
        setAssignee(assignee);

        addChangeToHistory(String.format(CREATION_MESSAGE, "Bug", id));
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

        for (int i = 1; i <= stepsToReproduce.size(); i++) {
            steps.append(stepsToReproduce.get(i)).append("\n");
        }

        return steps.toString();
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void increasePriority() {
        switch (priority) {
            case LOW:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.MEDIUM));
                priority = Priority.MEDIUM;
                break;
            case MEDIUM:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.HIGH));
                priority = Priority.HIGH;
                break;
            case HIGH:
                throw new IllegalArgumentException("Cannot increase priority beyond High.");
        }
    }

    @Override
    public void decreasePriority() {
        switch (priority) {
            case LOW:
                throw new IllegalArgumentException("Cannot decrease priority further than Low.");
            case MEDIUM:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.LOW));
                priority = Priority.LOW;
                break;
            case HIGH:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.MEDIUM));
                priority = Priority.MEDIUM;
                break;
        }
    }

    @Override
    public void setPriority(Priority priority) {
        if (this.priority == null) {
            this.priority = priority;
        } else if (!this.priority.equals(priority)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", this.priority, priority));
            this.priority = priority;
        }
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
    public void setAssignee(String assignee) {

    }

    @Override
    public String displayDetails() {
        return super.displayDetails();
    }
}
