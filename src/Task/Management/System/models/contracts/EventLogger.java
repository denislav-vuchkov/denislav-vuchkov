package Task.Management.System.models.contracts;

public interface EventLogger {

    String CHANGE_MESSAGE = "%s changed from %s to %s.";
    String CREATION_MESSAGE = "%s: %s created.";
    String IMPOSSIBLE_CHANGE_MESSAGE = "%s is already %s.";

    void addEvent(String description);

    String getEvents();

    int size();

}
