package Task_Management_System.models.logger.contracts;

import java.time.LocalDateTime;

public interface Event {

    LocalDateTime getOccurrence();

    String toString();

}