package Task_Management_System.commands.sort;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Feedback;
import Task_Management_System.utils.ListHelpers;
import Task_Management_System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class SortFeedbacks extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public SortFeedbacks(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        List<Feedback> feedbacks = getRepository().getFeedbacks();
        if (feedbacks.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "feedbacks");
        }
        String criterion = parameters.get(0);
        switch (criterion.toUpperCase()) {
            case "TITLE":
                return ListHelpers.sortTasks(Comparator.comparing(Feedback::getTitle), feedbacks);
            case "RATING":
                return ListHelpers.sortTasks((Comparator.comparing(Feedback::getRating).reversed()), feedbacks);
            default:
                throw new InvalidUserInput(String.format(INVALID_SORT_PARAMETER, "Feedbacks", "title or rating"));
        }
    }
}
