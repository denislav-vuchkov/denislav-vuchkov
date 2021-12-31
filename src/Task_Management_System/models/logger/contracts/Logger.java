package Task_Management_System.models.logger.contracts;

import Task_Management_System.models.logger.EventImpl;

import java.util.List;

public interface Logger {

    String TEAM = "Team";
    String BOARD = "Board";
    String USER = "User";

    String CREATION = "%s %s: Created.";
    String ADDITION = "%s %s added.";
    String REMOVAL = "%s %s removed.";

    String USER_ADD_TASK = "User %s: %s with ID %d added to list of tasks.";
    String USER_REMOVE_TASK = "User %s: %s with ID %d removed from list of tasks.";

    String BOARD_ADD_TASK = "Board %s: %s with ID %d added.";
    String BOARD_REMOVE = "Board %s: %s with ID %d removed.";

    String TASK_CREATED = "%s with ID %d: Created.";
    String TASK_CHANGE = "%s with ID %d: %s changed from %s to %s.";
    String DUPLICATE = "%s with ID %d: Modification denied. %s is already %s.";

    void addEvent(String description);

    List<EventImpl> getEvents();

    int size();

}
