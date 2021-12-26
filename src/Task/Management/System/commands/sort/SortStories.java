package Task.Management.System.commands.sort;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.utils.ListHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class SortStories extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public SortStories(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        List<Story> stories = getRepository().getStories();

        if (stories.isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "stories");
        }

        String criterion = parameters.get(0);

        switch (criterion.toUpperCase()) {
            case "TITLE":
                return ListHelpers.sort(Comparator.comparing(Story::getTitle), stories);
            case "PRIORITY":
                return ListHelpers.sort(Comparator.comparing(Story::getPriority), stories);
            case "SIZE":
                return ListHelpers.sort(Comparator.comparing(Story::getSize), stories);
            default:
                throw new InvalidUserInput(String.format(INVALID_SORT_PARAMETER, "Stories", "title, priority or size"));
        }
    }
}
