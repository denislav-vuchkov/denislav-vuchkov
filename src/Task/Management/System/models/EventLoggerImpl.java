package Task.Management.System.models;

import Task.Management.System.models.contracts.EventLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventLoggerImpl implements EventLogger {

    private final List<String> events;

    public EventLoggerImpl() {
        events = new ArrayList<>();
    }

    @Override
    public void addEvent(String description) {
        String eventEntry = String.format("[%s - %s]",
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()),
                description);
        events.add(eventEntry);
    }

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public String getEvents() {
        StringBuilder completeHistory = new StringBuilder();
        events.forEach(entry -> completeHistory.append(entry).append(System.lineSeparator()));
        return completeHistory.toString().trim();
    }
}
