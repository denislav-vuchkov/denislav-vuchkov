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

    public static final int TITLE_MIN_LENGTH = 10;
    public static final int TITLE_MAX_LENGTH = 50;
    public static final String INVALID_NAME_MESSAGE = String.format("Title must be between %d and %d symbols.",
            TITLE_MIN_LENGTH, TITLE_MAX_LENGTH);

    public static final int DESCRIPTION_MIN_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final String INVALID_DESCRIPTION_MESSAGE =
            String.format("Description must be between %d and %d symbols.",
                    DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);

    public static final String COMMENTS_HEADER = "--COMMENTS--";
    public static final String HISTORY_HEADER = "--HISTORY--";
    public static final String NO_COMMENTS_HEADER = "--NO COMMENTS--";

    private final long id;
    private final List<Comment> comments;
    private final EventLogger history;
    private String title;
    private String description;
    private TaskStatus status;

    public TaskBase(long id, Tasks tasksType, String title, String description, TaskStatus status) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setStatus(status);
        this.comments = new ArrayList<>();
        this.history = new EventLoggerImpl();

        addChangeToHistory(String.format(CREATION,
                super.getClass().getSimpleName().replace("Base", ""),
                tasksType.toString()));
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        ValidationHelpers.validateRange(title.length(), TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, INVALID_NAME_MESSAGE);
        if (this.title == null) {
            this.title = title;
            return;
        }
        if (this.title.equals(title)) {
            throw new InvalidUserInput(String.format(DUPLICATE, "Title", this.title));
        }
        history.addEvent(String.format(CHANGE, "Title", this.title, title));
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        ValidationHelpers.validateRange(description.length(),
                DESCRIPTION_MIN_LENGTH,
                DESCRIPTION_MAX_LENGTH,
                INVALID_DESCRIPTION_MESSAGE);
        if (this.description == null) {
            this.description = description;
            return;
        }
        if (this.description.equals(description)) {
            throw new InvalidUserInput(String.format(DUPLICATE, "Description", this.description));
        }
        history.addEvent(String.format(CHANGE, "Description", this.description, description));
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
        history.addEvent(String.format(CHANGE, "Status", this.status, status));
        this.status = status;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String printComments() {
        StringBuilder output = new StringBuilder();

        if (comments.isEmpty()) {
            output.append(NO_COMMENTS_HEADER);
        } else {
            output.append(COMMENTS_HEADER);
            output.append("\n");
            comments.forEach(output::append);
            output.append(COMMENTS_HEADER);
        }
        return output.toString();
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
