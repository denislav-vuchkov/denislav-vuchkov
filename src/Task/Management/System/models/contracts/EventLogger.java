package Task.Management.System.models.contracts;

import Task.Management.System.models.Event;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface EventLogger {

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

    static <B extends Loggable, U extends Loggable> List<Event>
    extract(List<Event> history, List<B> boards, List<U> users) {
        history.addAll(getEvents(boards));
        history.addAll(getEvents(users));
        return history.stream().sorted(Comparator.comparing(Event::getOccurrence)).collect(Collectors.toList());
    }

    static <U extends Loggable> List<Event> extract(List<Event> history, List<U> users) {
        history.addAll(getEvents(users));
        return history.stream().sorted(Comparator.comparing(Event::getOccurrence)).collect(Collectors.toList());
    }

    static List<Event> extract(List<Event> history) {
        return history.stream().sorted(Comparator.comparing(Event::getOccurrence)).collect(Collectors.toList());
    }

    static <T extends Loggable> List<Event> getEvents(List<T> elements) {
        return elements.stream().flatMap(element -> element.getLog().stream()).collect(Collectors.toList());
    }

    void addEvent(String description);

    List<Event> getEvents();

    int size();

}
