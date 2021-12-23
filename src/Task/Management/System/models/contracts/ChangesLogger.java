package Task.Management.System.models.contracts;

public interface ChangesLogger {

    String CHANGE_MESSAGE = "%s changed from %s to %s.";
    String CREATION_MESSAGE = "%s: %s created.";
    String IMPOSSIBLE_CHANGE_MESSAGE = "%s is already %s.";

    String getCompleteHistory();

    void addChange(String description);

    int size();

}
