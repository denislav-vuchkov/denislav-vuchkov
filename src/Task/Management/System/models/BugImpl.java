package Task.Management.System.models;

import Task.Management.System.models.contracts.Bug;
import Task.Management.System.models.enums.BugStatus;
import Task.Management.System.models.enums.Priority;
import Task.Management.System.models.enums.Severity;

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
        this.priority = priority;
        this.severity = severity;
        this.assignee = assignee;


    }

    @Override
    public void setStatus(BugStatus status) {
        if (!this.status.equals(status)) {
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

    }

    @Override
    public void retractStatus() {

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

    }

    @Override
    public void decreasePriority() {

    }

    @Override
    public void setPriority(Priority priority) {

    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public void increaseSeverity() {

    }

    @Override
    public void decreaseSeverity() {

    }

    @Override
    public void setSeverity(Severity severity) {

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
