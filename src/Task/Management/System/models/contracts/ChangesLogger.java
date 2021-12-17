package Task.Management.System.models.contracts;

import java.time.LocalDateTime;

public interface ChangesLogger {

    String CHANGE_MESSAGE = "%s changed from %s to %s.";
    String CREATION_MESSAGE = "%s: %s created.";
    String IMPOSSIBLE_CHANGE_MESSAGE = "%s is already at %s.";

    String getCompleteHistory();

    String getChangeAt(int index);

    void addChange(String description);

    int size();

}
