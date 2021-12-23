package Task.Management.System.models.teams.contracts;

import Task.Management.System.models.tasks.contracts.Task;

import java.util.List;

public interface TaskHandler<T extends Task> {

    List<T> getTasks();

    void addTask(T task);

    void removeTask(T task);

}
