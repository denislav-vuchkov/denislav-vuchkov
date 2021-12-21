package Task.Management.System.commands;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public abstract class BaseCommand implements Command {

    private final TaskManagementSystemRepository repository;

    protected BaseCommand(TaskManagementSystemRepository repository) {
        this.repository = repository;
    }

    protected TaskManagementSystemRepository getRepository() {
        return repository;
    }

    @Override
    public String execute(List<String> parameters) {
        return executeCommand(parameters);
    }

    protected abstract String executeCommand(List<String> parameters);
}
