package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.AssignableTask;
import Task.Management.System.models.tasks.contracts.TaskStatus;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Tasks;

import static Task.Management.System.models.logger.contracts.Logger.TASK_CHANGE;

public abstract class AssignableTaskImpl extends TaskBase implements AssignableTask {

    public static final String UNASSIGNED = "Unassigned";

    public static final String PRIORITY_FIELD = "Priority";
    public static final String ASSIGNEE_FIELD = "Assignee";
    private final Tasks taskType;
    private Priority priority;
    private String assignee;

    public AssignableTaskImpl(long id, Tasks tasksType, String title, String description,
                              Priority priority, TaskStatus status) {
        super(id, tasksType, title, description, status);
        taskType = tasksType;
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
        checkForDuplication(getPriority(), priority, PRIORITY_FIELD);
        logActivity(String.format(TASK_CHANGE, taskType, getID(), PRIORITY_FIELD, getPriority(), priority));
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
        checkForDuplication(getAssignee(), assignee, ASSIGNEE_FIELD);
        logActivity(String.format(TASK_CHANGE, taskType, getID(), ASSIGNEE_FIELD, this.assignee, assignee));
        this.assignee = assignee;
    }

    @Override
    public void unAssign() {
        setAssignee(UNASSIGNED);
    }


}