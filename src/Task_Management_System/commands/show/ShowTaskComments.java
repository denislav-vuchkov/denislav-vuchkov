package Task_Management_System.commands.show;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.tasks.contracts.Comment;
import Task_Management_System.models.tasks.contracts.Task;
import Task_Management_System.utils.ParsingHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;


public class ShowTaskComments extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTaskComments(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        long ID = ParsingHelpers.tryParseLong(parameters.get(0), INVALID_ID);

        Task task = getRepository().findTask(ID);

        if (task.getComments().isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "comments");
        }

        return task.
                getComments()
                .stream()
                .map(Comment::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
