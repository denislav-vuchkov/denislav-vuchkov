package Task.Management.System.models.contacts;

public interface Changeable {

    String TEAM_HISTORY_HEADER = "%s %s activity:";
    String TEAM_HISTORY_EMPTY = "Nothing to display";

    String BOARD_TASK_ADDED = "%s %s added to %s %s.";
    String BOARD_TASK_REMOVED = "%s %s removed to %s.";

    String MEMBER_TASK_ASSIGNED = "%s %s assigned to %s %s.";
    String MEMBER_TASK_UNASSIGNED = "%s %s unassigned to %s %s.";

    String getHistory();
}
