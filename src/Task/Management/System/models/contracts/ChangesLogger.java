package Task.Management.System.models.contracts;

import java.time.LocalDateTime;

public interface ChangesLogger {

    String getCompleteHistory();

    String getChangeAt(int index);

    boolean addChange(String description, LocalDateTime localDateTime);

}
