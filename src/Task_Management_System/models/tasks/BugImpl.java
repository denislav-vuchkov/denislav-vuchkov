package Task_Management_System.models.tasks;

import Task_Management_System.models.logger.EventImpl;
import Task_Management_System.models.tasks.contracts.Bug;
import Task_Management_System.models.tasks.enums.BugStatus;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Severity;
import Task_Management_System.models.tasks.enums.Tasks;
import Task_Management_System.utils.FormatHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BugImpl extends AssignableTaskImpl implements Bug {

    public static final String SEVERITY_FIELD = "Severity";
    public static final String STEPS_HEADER = "Steps to reproduce:";

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

    public String printStepsToReproduce() {
        int[] counter = new int[1];

        StringBuilder output = new StringBuilder();
        output.append(STEPS_HEADER);
        stepsToReproduce.forEach(e -> output.append(String.format("%n%d. %s", ++counter[0], e)));

        return output.toString();
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(Severity severity) {
        checkForDuplication(getSeverity(), severity, SEVERITY_FIELD);
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
                        "%s%n" +
                        "%s%n" +
                        "%s",
                FormatHelpers.getType(this),
                super.printDetails(),
                getPriority(), getSeverity(), getStatus(), getAssignee(),
                printStepsToReproduce(),
                printComments(),
                CHANGES_HISTORY +
                        getLog().stream().map(EventImpl::toString).collect(Collectors.joining(System.lineSeparator())));
    }
}
