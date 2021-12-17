package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.BugsAndStoryIntermediate;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Tasks;

import static Task.Management.System.models.contacts.ChangesLogger.CHANGE_MESSAGE;
import static Task.Management.System.models.contacts.ChangesLogger.IMPOSSIBLE_CHANGE_MESSAGE;

public abstract class BugsAndStoryIntermediateImpl extends TaskBase implements BugsAndStoryIntermediate {

    private Priority priority;
    private String assignee;

    public BugsAndStoryIntermediateImpl(int id, Tasks tasksType, String title, String description, Priority priority, String assignee) {
        super(id, tasksType, title, description);
        setPriority(priority);
        setAssignee(assignee);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void increasePriority() {
        switch (priority) {
            case LOW:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.MEDIUM));
                priority = Priority.MEDIUM;
                break;
            case MEDIUM:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.HIGH));
                priority = Priority.HIGH;
                break;
            case HIGH:
                throw new IllegalArgumentException("Cannot increase priority beyond High.");
        }
    }

    @Override
    public void decreasePriority() {
        switch (priority) {
            case LOW:
                throw new IllegalArgumentException("Cannot decrease priority further than Low.");
            case MEDIUM:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.LOW));
                priority = Priority.LOW;
                break;
            case HIGH:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.MEDIUM));
                priority = Priority.MEDIUM;
                break;
        }
    }

    @Override
    public void setPriority(Priority priority) {
        if (this.priority == null) {
            this.priority = priority;
        } else if (!this.priority.equals(priority)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", this.priority, priority));
            this.priority = priority;
        }
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    @Override
    public void setAssignee(String assignee) {
        if (this.assignee == null) {
            this.assignee = assignee;
        } else if (!this.assignee.equals(assignee)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Assignee", this.assignee, assignee));
            this.assignee = assignee;
        } else {
            throw new IllegalArgumentException(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Assignee", this.assignee));
        }
    }

    @Override
    public void unAssign() {
        this.assignee = "Unassigned";
    }

}
