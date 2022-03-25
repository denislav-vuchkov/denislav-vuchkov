package main.models.tasks.contracts;

import main.models.tasks.enums.Severity;

import java.util.List;

public interface Bug extends AssignableTask {

    List<String> getStepsToReproduce();

    Severity getSeverity();

    void setSeverity(Severity severity);

}
