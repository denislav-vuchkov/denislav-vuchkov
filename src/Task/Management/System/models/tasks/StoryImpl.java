package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.models.tasks.enums.Tasks;

import java.util.stream.Collectors;

import static Task.Management.System.models.contracts.EventLogger.CHANGE;
import static Task.Management.System.models.contracts.EventLogger.DUPLICATE;

public class StoryImpl extends AssignableTaskImpl implements Story {

    private Size size;

    public StoryImpl(long id, String title, String description, Priority priority, Size size) {
        super(id, Tasks.STORY, title, description, priority,StoryStatus.NOT_DONE);
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
        if (this.size.equals(size)) {
            throw new InvalidUserInput(String.format(DUPLICATE, "Size", this.size));
        }
        addChangeToHistory(String.format(CHANGE, "Size", this.size, size));
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                this.getClass().getSimpleName().replace("Impl", ""),
                getID(), getTitle(), getPriority(), getSize(),
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
                this.getClass().getSimpleName().replace("Impl", ""),
                super.printDetails(),
                getPriority(), getSize(), getStatus(), getAssignee(),
                getComments().stream().map(Comment::toString).collect(Collectors.joining(System.lineSeparator())),
                getLog());
    }
}
