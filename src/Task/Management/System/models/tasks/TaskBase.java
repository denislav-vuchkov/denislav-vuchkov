package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.Event;
import Task.Management.System.models.EventLoggerImpl;
import Task.Management.System.models.contracts.EventLogger;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.tasks.contracts.TaskStatus;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.EventLogger.*;

public abstract class TaskBase implements Task {

    public static final int TITLE_MIN = 10;
    public static final int TITLE_MAX = 50;
    public static final String TITLE_ERR =
            String.format("Title must be between %d and %d symbols.", TITLE_MIN, TITLE_MAX);

    public static final int DESCRIPTION_MIN = 10;
    public static final int DESCRIPTION_MAX = 500;
    public static final String DESCRIPTION_ERR =
            String.format("Description must be between %d and %d symbols.", DESCRIPTION_MIN, DESCRIPTION_MAX);
    public static final String COMMENT_ADDED = "%s with ID %d: Comment added with author %s.";

    private final long id;
    private String title;
    private String description;
    private TaskStatus status;
    private final List<Comment> comments;
    private final EventLogger history;
    private Tasks taskType;

    public TaskBase(long id, Tasks tasksType, String title, String description, TaskStatus status) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setStatus(status);
        this.comments = new ArrayList<>();
        this.history = new EventLoggerImpl();
        this.taskType = tasksType;
        addChangeToHistory(String.format(TASK_CREATED, tasksType.toString(), getID()));
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        ValidationHelpers.validateRange(title.length(), TITLE_MIN, TITLE_MAX, TITLE_ERR);
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        ValidationHelpers.validateRange(description.length(), DESCRIPTION_MIN, DESCRIPTION_MAX, DESCRIPTION_ERR);
        this.description = description;
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public void setStatus(TaskStatus status) {
        if (this.status == null) {
            this.status = status;
            return;
        }
        if (this.status.equals(status)) {
            throw new InvalidUserInput(String.format(DUPLICATE, "Status", getStatus()));
        }
        history.addEvent(String.format(CHANGE, taskType, getID(), "Status", this.status, status));
        this.status = status;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
        history.addEvent(String.format(COMMENT_ADDED, taskType, getID(),
                comment.getAuthor()));
    }

    @Override
    public List<Event> getLog() {
        return new ArrayList<>(history.getEvents());
    }

    protected void addChangeToHistory(String description) {
        history.addEvent(description);
    }

    @Override
    public String printDetails() {
        return String.format("ID: %d%n" +
                        "Title: %s%n" +
                        "Description: %s%n",
                getID(), getTitle(), getDescription());
    }
}
