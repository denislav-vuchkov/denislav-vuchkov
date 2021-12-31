package Task_Management_System.models.logger;

import Task_Management_System.models.logger.contracts.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventImpl implements Event {

    private final long occurrence;
    private final LocalDateTime time;
    private final String description;

    public EventImpl(String message) {
        occurrence = System.nanoTime();
        time = LocalDateTime.now();
        description = message;
    }

    public long getOccurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",
                DateTimeFormatter.
                        ofPattern("dd/MM/yyyy HH:mm:ss").
                        format(time), description);
    }
}
