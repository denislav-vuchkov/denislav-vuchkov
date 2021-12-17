package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;
import Task.Management.System.models.tasks.enums.Tasks;

import static Task.Management.System.models.contracts.ChangesLogger.*;

public class StoryImpl extends BugsAndStoryIntermediateImpl implements Story {

    private StoryStatus status;
    private Priority priority;
    private Size size;
    private String assignee;

    public StoryImpl(int id, String title, String description, Priority priority, Size size, String assignee) {
        super(id, Tasks.STORY, title, description, priority, assignee);
        this.status = StoryStatus.NOT_DONE;
        setSize(size);
    }

    public StoryImpl(int id, String title, String description, Priority priority, Size size) {
        this(id, title, description, priority, size,  "Unassigned");
    }


    @Override
    public void setStatus(StoryStatus status) {
        if (!this.status.equals(status)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Status", this.status, status));
            this.status = status;
        } else {
            throw new IllegalArgumentException(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Status", this.status));
        }
    }

    @Override
    public String getStatus() {
        return status.toString();
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
                throw new IllegalArgumentException("Cannot advance status from fixed.");
        }

    }

    @Override
    public void retractStatus() {
        switch (this.status) {
            case NOT_DONE:
                throw new IllegalArgumentException("Cannot revert further than not done.");
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
        return null;
    }

    @Override
    public void setSize(Size size) {

    }

    @Override
    public String displayDetails() {
        return String.format("Task type: %s%n" +
                        "%s" +
                        "Priority: %s%n" +
                        "Size: %s%n" +
                        "Status: %s%n" +
                        "Assignee: %s%n" +
                        "%s" +
                        "%s",
                this.getClass().getSimpleName().replace("Impl", ""),
                super.displayDetails(),
                getPriority(), getSize(), getStatus(), getAssignee(),
                displayComments(),
                getHistory());
    }
}
