package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Tasks;

import static Task.Management.System.models.contracts.EventLogger.CHANGE_MESSAGE;
import static Task.Management.System.models.contracts.EventLogger.IMPOSSIBLE_CHANGE_MESSAGE;

public abstract class AssignableTaskImpl extends TaskBase implements AssignableTask {

    public static final String UNASSIGNED = "Unassigned";
    private Priority priority;
    private String assignee;

    public AssignableTaskImpl(long id, Tasks tasksType, String title, String description, Priority priority) {
        super(id, tasksType, title, description);
        setPriority(priority);
        setAssignee(UNASSIGNED);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void setPriority(Priority priority) {
        if (this.priority == null) {
            this.priority = priority;
            return;
        }
        if (this.priority.equals(priority)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Priority", this.priority));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", this.priority, priority));
        this.priority = priority;
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    @Override
    public void setAssignee(String assignee) {
        if (this.assignee == null) {
            this.assignee = assignee;
            return;
        }
        if (this.assignee.equals(assignee)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Assignee", this.assignee));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Assignee", this.assignee, assignee));
        this.assignee = assignee;
    }

    @Override
    public void unAssign() {
        setAssignee(UNASSIGNED);
    }
}