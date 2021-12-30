package Task.Management.System.models.tasks;

import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.logger.EventImpl;
import Task.Management.System.models.logger.LoggerImpl;
import Task.Management.System.models.logger.contracts.Logger;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.tasks.contracts.TaskStatus;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Task.Management.System.models.logger.contracts.Logger.*;

public abstract class TaskBase implements Task {

    public static final int TITLE_MIN = 10;
    public static final int TITLE_MAX = 50;
    public static final String TITLE_ERR = String.format(
            "Title must be between %d and %d symbols.", TITLE_MIN, TITLE_MAX);

    public static final int DESCRIPTION_MIN = 10;
    public static final int DESCRIPTION_MAX = 500;
    public static final String DESCRIPTION_ERR = String.format(
            "Description must be between %d and %d symbols.", DESCRIPTION_MIN, DESCRIPTION_MAX);

    public static final String COMMENT_ADDED = "%s with ID %d: Comment added by user %s.";
    public static final String STATUS_FIELD = "Status";
    public static final String NO_COMMENTS_MESSAGE = "Comments: No comments on this task.";
    public static final String COMMENTS_HEADER = "Comments:\n";
    public static final String CHANGES_HISTORY = "History of changes:\n";

    private final long id;
    private final List<Comment> comments;
    private final Logger history;
    private final Tasks taskType;
    private String title;
    private String description;
    private TaskStatus status;

    public TaskBase(long id, Tasks tasksType, String title, String description, TaskStatus status) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setStatus(status);
        this.comments = new ArrayList<>();
        this.history = new LoggerImpl();
        this.taskType = tasksType;
        logActivity(String.format(TASK_CREATED, tasksType.toString(), getID()));
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
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(TaskStatus status) {
        checkForDuplication(getStatus(), status, STATUS_FIELD);
        this.status = status;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    protected String printComments() {
        if (comments.isEmpty()) {
            return NO_COMMENTS_MESSAGE;
        } else {
            return COMMENTS_HEADER +
                    comments.stream().map(Comment::toString).collect(Collectors.joining(System.lineSeparator()));
        }
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
        history.addEvent(String.format(COMMENT_ADDED, taskType, getID(), comment.getAuthor()));
    }

    @Override
    public List<EventImpl> getLog() {
        return new ArrayList<>(history.getEvents());
    }

    @Override
    public String printDetails() {
        return String.format("ID: %d%n" +
                        "Title: %s%n" +
                        "Description: %s%n",
                getID(), getTitle(), getDescription());
    }

    protected void logActivity(String description) {
        history.addEvent(description);
    }

    protected <T> void checkForDuplication(T currentValue, T newValue, String property) {
        if (Objects.isNull(currentValue)) return;

        if (newValue.equals(currentValue)) {
            String changeDenied = String.format(DUPLICATE, taskType, getID(), property, currentValue);
            history.addEvent(changeDenied);
            throw new InvalidUserInput(changeDenied);
        }

        String changeSuccessful = String.format(TASK_CHANGE, taskType, getID(), property, currentValue, newValue);
        history.addEvent(changeSuccessful);
    }

}
