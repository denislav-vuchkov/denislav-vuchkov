package Task_Management_System.models.tasks;

import Task_Management_System.models.logger.EventImpl;
import Task_Management_System.models.tasks.contracts.Story;
import Task_Management_System.models.tasks.enums.Priority;
import Task_Management_System.models.tasks.enums.Size;
import Task_Management_System.models.tasks.enums.StoryStatus;
import Task_Management_System.models.tasks.enums.Tasks;
import Task_Management_System.utils.FormatHelpers;

import java.util.stream.Collectors;

public class StoryImpl extends AssignableTaskImpl implements Story {

    public static final String SIZE_FIELD = "Size";

    private Size size;

    public StoryImpl(long id, String title, String description, Priority priority, Size size) {
        super(id, Tasks.STORY, title, description, priority, StoryStatus.NOT_DONE);
        setSize(size);
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void setSize(Size size) {
        checkForDuplication(getSize(), size, SIZE_FIELD);
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                FormatHelpers.getType(this), getID(), getTitle(), getPriority(), getSize(),
                getStatus(), getAssignee(), getComments().size());
    }

    @Override
    public String printDetails() {
        return String.format("Task type: %s%n" +
                        "%s" +
                        "Priority: %s%n" +
                        "Size: %s%n" +
                        "Status: %s%n" +
                        "Assignee: %s%n" +
                        "%s%n" +
                        "%s",
                FormatHelpers.getType(this),
                super.printDetails(),
                getPriority(), getSize(), getStatus(), getAssignee(),
                printComments(),
                CHANGES_HISTORY +
                        getLog().stream().map(EventImpl::toString).collect(Collectors.joining(System.lineSeparator())));
    }
}
