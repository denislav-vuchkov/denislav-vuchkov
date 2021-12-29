package Task.Management.System.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Event {

    private final LocalDateTime occurrence;
    private final String description;

    public Event(String description) {
        try {
            Thread.sleep(0, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
