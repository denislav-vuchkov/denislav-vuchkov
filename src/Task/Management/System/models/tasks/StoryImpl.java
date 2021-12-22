package Task.Management.System.models.tasks;

import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.models.tasks.enums.Tasks;

import static Task.Management.System.models.contracts.ChangesLogger.CHANGE_MESSAGE;
import static Task.Management.System.models.contracts.ChangesLogger.IMPOSSIBLE_CHANGE_MESSAGE;

public class StoryImpl extends AssignableTaskImpl implements Story {

    public static final String LOWER_BOUNDARY = "Cannot decrease %s further than %s.";
    public static final String UPPER_BOUNDARY = "Cannot increase %s beyond %s.";

    private StoryStatus status;
    private Size size;

    public StoryImpl(int id, String title, String description, Priority priority, Size size, String assignee) {
        super(id, Tasks.STORY, title, description, priority, assignee);
        setSize(size);
        setStatus(StoryStatus.NOT_DONE);
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public void setStatus(StoryStatus status) {
        if (this.status == null) {
            this.status = status;
            return;
        }
        if (this.status.equals(status)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Status", this.status));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, status));
        this.status = status;
    }

    @Override
    public void advanceStatus() {
        switch (this.status) {
            case NOT_DONE:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, StoryStatus.IN_PROGRESS));
                this.status = StoryStatus.IN_PROGRESS;
                break;
            case IN_PROGRESS:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, StoryStatus.DONE));
                this.status = StoryStatus.DONE;
                break;
            case DONE:
                throw new InvalidUserInput(String.format(UPPER_BOUNDARY, "status", StoryStatus.DONE));
        }
    }

    @Override
    public void retractStatus() {
        switch (this.status) {
            case NOT_DONE:
                throw new InvalidUserInput(String.format(LOWER_BOUNDARY, "status", StoryStatus.NOT_DONE));
            case IN_PROGRESS:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, StoryStatus.NOT_DONE));
                this.status = StoryStatus.NOT_DONE;
                break;
            case DONE:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, StoryStatus.IN_PROGRESS));
                this.status = StoryStatus.IN_PROGRESS;
                break;
        }
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
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Size", this.size));
        }
        addChangeToHistory(String.format(CHANGE_MESSAGE, "Size", this.size, size));
        this.size = size;
    }

    @Override
    public String displayAllDetails() {
        return String.format("Task type: %s%n" +
                        "%s" +
                        "Priority: %s%n" +
                        "Size: %s%n" +
                        "Status: %s%n" +
                        "Assignee: %s%n" +
                        "%s" +
                        "%s",
                this.getClass().getSimpleName().replace("Impl", ""),
                super.displayAllDetails(),
                getPriority(), getSize(), getStatus(), getAssignee(),
                displayComments(),
                getHistory());
    }

    @Override
    public String toString() {
        return String.format("Task type: %s - Title: %s - Priority: %s - Size: %s - Status - %s - Assignee - %s",
                this.getClass().getSimpleName().replace("Impl", ""),
                getTitle(), getPriority(), getSize(), getStatus(), getAssignee());
    }
}
