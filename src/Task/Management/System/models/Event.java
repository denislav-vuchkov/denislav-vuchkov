package Task.Management.System.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Event {
    private final LocalDateTime occurrence;
    private final String description;

    public Event(String description) {
        this.occurrence = LocalDateTime.now();
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",
                DateTimeFormatter.
                        ofPattern("dd/MM/yyyy HH:mm:ss").
                        format(occurrence), description);
    }
}
