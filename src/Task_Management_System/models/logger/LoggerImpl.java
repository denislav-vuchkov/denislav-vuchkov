package Task_Management_System.models.logger;

import Task_Management_System.models.logger.contracts.Logger;

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
    public void addEvent(String description, String teamName) {
        events.add(new EventImpl(description, teamName));
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
