package Task_Management_System.models.logger;

import Task_Management_System.models.logger.contracts.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventImpl implements Event {

    private final LocalDateTime occurrence;
    private final String description;

    public EventImpl(String description) {
        this.occurrence = LocalDateTime.now();
        this.description = description;
    }

    public LocalDateTime getOccurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",
                DateTimeFormatter.
                        ofPattern("dd/MM/yyyy HH:mm:ss").
                        format(occurrence), description);
    }
}
