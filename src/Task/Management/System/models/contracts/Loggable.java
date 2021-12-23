package Task.Management.System.models.contracts;

public interface Loggable {

    String TEAM_HISTORY_HEADER = "%s %s activity:";
    String TEAM_HISTORY_EMPTY = "Nothing to display";

    String getLog();

}
