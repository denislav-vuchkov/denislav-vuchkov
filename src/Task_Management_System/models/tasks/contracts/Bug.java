package Task_Management_System.models.tasks.contracts;

import Task_Management_System.models.tasks.enums.Severity;

import java.util.List;

public interface Bug extends AssignableTask {

    List<String> getStepsToReproduce();

    Severity getSeverity();

    void setSeverity(Severity severity);

}
