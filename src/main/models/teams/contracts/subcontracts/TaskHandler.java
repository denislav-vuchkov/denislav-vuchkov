package main.models.teams.contracts.subcontracts;

import main.models.tasks.contracts.Task;

import java.util.List;

public interface TaskHandler<T extends Task> {

    List<T> getTasks();

    void addTask(T task);

    void removeTask(T task);

}
