package Task.Management.System.models;

import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.contracts.Loggable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EventLoggerImpl implements EventLogger {

    private final List<Event> events;

    public EventLoggerImpl() {
        events = new ArrayList<>();
    }

    @Override
    public void addEvent(String description) {
        events.add(new Event(description));
    }

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }
}
