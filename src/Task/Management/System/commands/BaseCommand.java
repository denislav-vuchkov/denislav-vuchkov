package Task.Management.System.commands;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public abstract class BaseCommand implements Command {

    private static final String NOT_EXIST = "The %s does not exist! Create a %s with this name first.";
    public static final String TEAM_DOES_NOT_EXIST = String.format(NOT_EXIST, "team", "team");
    public static final String BOARD_DOES_NOT_EXIST = String.format(NOT_EXIST, "board", "board");
    public static final String USER_DOES_NOT_EXIST = String.format(NOT_EXIST, "user", "user");

    private static final String ALREADY_EXISTS = "This %s name already exists! Please choose a unique %s name.";
    public static final String TEAM_ALREADY_EXISTS = String.format(NOT_EXIST, "team", "team");
    public static final String BOARD_ALREADY_EXISTS = String.format(NOT_EXIST, "board", "board");
    public static final String USER_ALREADY_EXISTS = String.format(NOT_EXIST, "user", "user");

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
