package Task_Management_System.models.logger;

import Task_Management_System.models.logger.contracts.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static Task_Management_System.models.teams.UserImpl.TEAM_NOT_APPLICABLE;

public class EventImpl implements Event {


    private final long occurrence;
    private final LocalDateTime time;
    private final String description;
    private String team = TEAM_NOT_APPLICABLE;

    public EventImpl(String message) {
        occurrence = System.nanoTime();
        time = LocalDateTime.now();
        description = message;
    }

    public EventImpl(String message, String team) {
        this(message);
        this.team = team;
    }

    @Override
    public long getOccurrence() {
        return occurrence;
    }

    @Override
    public String getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",
                DateTimeFormatter.
                        ofPattern("dd/MM/yyyy HH:mm:ss").
                        format(time), description);
    }
}
