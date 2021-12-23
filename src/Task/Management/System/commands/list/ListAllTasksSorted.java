package Task.Management.System.commands.list;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ListAllTasksSorted extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    public static final String HEADER = "--TASKS SORTED BY TITLE--";
    public static final String NO_TASKS_EXIST = "There are no tasks in the system!";

    public ListAllTasksSorted(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (getRepository().getTasks().isEmpty()) {
            return NO_TASKS_EXIST;
        }

        StringBuilder output = new StringBuilder();

        output.append(HEADER).append("\n");
        getRepository()
                .getTasks()
                .stream()
                .sorted((left, right) -> left.getTitle().compareTo(right.getTitle()))
                .forEach(task -> output.append(task).append("\n"));
        output.append(HEADER);

        return output.toString();
    }
}
