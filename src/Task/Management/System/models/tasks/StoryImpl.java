package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.FormatHelpers;

import java.util.stream.Collectors;

import static Task.Management.System.models.logger.contracts.Logger.TASK_CHANGE;

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
        if (this.size == null) {
            this.size = size;
            return;
        }
        checkForDuplication(getSize(), size, SIZE_FIELD);
        logActivity(String.format(TASK_CHANGE, Tasks.STORY, getID(), SIZE_FIELD, this.size, size));
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
                        "%s" +
                        "%s",
                FormatHelpers.getType(this),
                super.printDetails(),
                getPriority(), getSize(), getStatus(), getAssignee(),
                getComments().stream().map(Comment::toString).collect(Collectors.joining(System.lineSeparator())),
                getLog());
    }
}
