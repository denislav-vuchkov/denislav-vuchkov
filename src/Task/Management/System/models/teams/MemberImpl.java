package Task.Management.System.models.teams;

import Task.Management.System.models.ChangesLoggerImpl;
import Task.Management.System.models.contracts.ChangesLogger;
import Task.Management.System.models.contracts.Task;
import Task.Management.System.models.teams.contracts.Member;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.ChangesLogger.CREATION_MESSAGE;

public class MemberImpl implements Member {

    private final List<Task> assignedTasks;
    private final ChangesLogger historyOfChanges;
    private String name;

    public MemberImpl(String name) {
        setName(name);
        assignedTasks = new ArrayList<>();
        historyOfChanges = new ChangesLoggerImpl();
        historyOfChanges.addChange(String.format(
                CREATION_MESSAGE,
                getClass().getSimpleName().replace("Impl", " "),
                getName()));
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateIntRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, INVALID_NAME_MESSAGE);
        this.name = name;
    }

    @Override
    public List<Task> getAssignedTasks() {
        return new ArrayList<>(assignedTasks);
    }

    @Override
    public void assignTask(Task task) {
        historyOfChanges.addChange(String.format(
                MEMBER_TASK_ASSIGNED,
                getClass().getSimpleName().replace("Impl", ""),
                getName(),
                task.getClass().getSimpleName().replace("Impl", ""),
                task.getTitle()));
        assignedTasks.add(task);
    }

    @Override
    public void unassignTask(Task task) {
        historyOfChanges.addChange(String.format(
                MEMBER_TASK_UNASSIGNED,
                getClass().getSimpleName().replace("Impl", ""),
                getName(),
                task.getClass().getSimpleName().replace("Impl", ""),
                task.getTitle()));
        assignedTasks.remove(task);
    }

    @Override
    public String getHistory() {
        return historyOfChanges.getCompleteHistory();
    }
}
