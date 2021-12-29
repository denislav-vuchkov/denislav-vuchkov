package Task.Management.System.models.logger;

import Task.Management.System.models.logger.contracts.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoggerImpl implements Logger {

    private final List<EventImpl> events;

    public LoggerImpl() {
        events = new ArrayList<>();
    }

    @Override
    public void addEvent(String description) {
        events.add(new EventImpl(description));
    }

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public List<EventImpl> getEvents() {
        return new ArrayList<>(events);
    }
}
