package Task.Management.System.models;

import Task.Management.System.models.contracts.EventLogger;

import java.util.ArrayList;
import java.util.List;

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
