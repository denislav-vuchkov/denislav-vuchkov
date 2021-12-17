package Task.Management.System.models.contracts;

import java.time.LocalDateTime;

public interface ChangesLogger {

    String getCompleteHistory();

    String getChangeAt(int index);

    void addChange(String description);

    int size();

}
