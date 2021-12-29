package Task.Management.System.models.contracts;

import Task.Management.System.models.Event;

import java.util.List;

public interface EventLogger {

    String TEAM = "Team";
    String BOARD = "Board";
    String USER = "User";

    String TASK_CREATED = "%s with ID %d created.";
    String CREATION = "%s %s created.";
    String ADDITION = "%s %s added.";
    String REMOVAL = "%s %s removed.";
    String CHANGE = "%s with ID %d: %s changed from %s to %s.";
    String DUPLICATE = "%s is already %s.";

    String TASK_ADDED = "%s with ID %d added in board %s.";
    String TASK_REMOVED = "%s with ID %d removed from board %s.";
    String TASK_ASSIGNED = "%s with ID %d assigned to user %s.";
    String TASK_UNASSIGNED = "%s with ID %d unassigned from user %s.";

    void addEvent(String description);

    List<Event> getEvents();

    int size();

}
