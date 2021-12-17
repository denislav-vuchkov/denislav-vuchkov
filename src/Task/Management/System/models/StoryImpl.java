package Task.Management.System.models;

import Task.Management.System.models.contracts.Story;
import Task.Management.System.models.enums.Priority;
import Task.Management.System.models.enums.Size;
import Task.Management.System.models.enums.StoryStatus;

import java.util.List;

import static Task.Management.System.models.contracts.ChangesLogger.*;

public class StoryImpl extends TaskBase implements Story {

    private StoryStatus status;
    private Priority priority;
    private Size size;
    private String assignee;

    public StoryImpl(int id, String title, String description, Priority priority, Size size, String assignee) {
        super(id, title, description);
        this.status = StoryStatus.NOT_DONE;

        setPriority(priority);
        setSize(size);
        setAssignee(assignee);

        addChangeToHistory(String.format(CREATION_MESSAGE,
                super.getClass().getSimpleName().replace("Base", ""),
                this.getClass().getSimpleName().replace("Impl", "")));
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
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void increasePriority() {
        switch (priority) {
            case LOW:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.MEDIUM));
                priority = Priority.MEDIUM;
                break;
            case MEDIUM:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.HIGH));
                priority = Priority.HIGH;
                break;
            case HIGH:
                throw new IllegalArgumentException("Cannot increase priority beyond High.");
        }
    }

    @Override
    public void decreasePriority() {
        switch (priority) {
            case LOW:
                throw new IllegalArgumentException("Cannot decrease priority further than Low.");
            case MEDIUM:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.LOW));
                priority = Priority.LOW;
                break;
            case HIGH:
                addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", priority, Priority.MEDIUM));
                priority = Priority.MEDIUM;
                break;
        }
    }

    @Override
    public void setPriority(Priority priority) {
        if (this.priority == null) {
            this.priority = priority;
        } else if (!this.priority.equals(priority)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Priority", this.priority, priority));
            this.priority = priority;
        } else {
            throw new IllegalArgumentException(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Priority", this.priority));
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
    public String getAssignee() {
        return assignee;
    }

    @Override
    public void setAssignee(String assignee) {
        if (this.assignee == null) {
            this.assignee = assignee;
        } else if (!this.assignee.equals(assignee)) {
            addChangeToHistory(String.format(CHANGE_MESSAGE, "Assignee", this.assignee, assignee));
            this.assignee = assignee;
        } else {
            throw new IllegalArgumentException(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Assignee", this.assignee));
        }
    }

    @Override
    public void unAssign() {
        this.assignee = "Unassigned";
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
                historyOfChanges());
    }
}
