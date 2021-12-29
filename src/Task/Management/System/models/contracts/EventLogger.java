package Task.Management.System.models.contracts;

import Task.Management.System.models.Event;

import java.util.List;

public interface EventLogger {

    String TEAM = "Team";
    String BOARD = "Board";
    String USER = "User";

    String CREATION = "%s %s created.";
    String ADDITION = "%s %s added.";
    String REMOVAL = "%s %s removed.";
    String DUPLICATE = "%s is already %s.";

    String TASK_ASSIGNED = "%s with ID %d has been added to the list of tasks for user %s.";
    String TASK_UNASSIGNED = "%s with ID %d has been removed from the list of tasks for user %s.";

    String TASK_CREATED = "%s with ID %d: Created.";
    String TASK_CHANGE = "%s with ID %d: %s changed from %s to %s.";
    String TASK_ADDED = "%s with ID %d: Added in board %s.";
    String TASK_REMOVED = "%s with ID %d: Removed from board %s.";

    void addEvent(String description);

    List<Event> getEvents();

    int size();

}
