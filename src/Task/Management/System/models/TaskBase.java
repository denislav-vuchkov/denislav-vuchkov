package Task.Management.System.models;

import Task.Management.System.models.contracts.ChangesLogger;
import Task.Management.System.models.contracts.Comment;
import Task.Management.System.models.contracts.Task;
import Task.Management.System.models.enums.BugStatus;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskBase implements Task {

    public static final int NAME_MIN_LENGTH = 10;
    public static final int NAME_MAX_LENGTH = 50;
    public static final String INVALID_NAME_MESSAGE = String.format("Title must be between %d and %d symbols.",
            NAME_MIN_LENGTH, NAME_MAX_LENGTH);

    public static final int DESCRIPTION_MIN_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final String INVALID_DESCRIPTION_MESSAGE =
            String.format("Description must be between %d and %d symbols.",
            DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);
    public static final String COMMENTS_HEADER = "--COMMENTS--";

    private final int id;
    private String title;
    private String description;
    private final List<Comment> comments;
    private final ChangesLogger historyOfChanges;

    public TaskBase(int id, String title, String description) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        this.comments = new ArrayList<>();
        this.historyOfChanges = new ChangesLoggerImpl();
    }

    public void setTitle(String title) {
        ValidationHelpers.validateIntRange(title.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, INVALID_NAME_MESSAGE);
        this.title = title;
    }

    public void setDescription(String description) {
        ValidationHelpers.validateIntRange(description.length(),
                DESCRIPTION_MIN_LENGTH,
                DESCRIPTION_MAX_LENGTH,
                INVALID_DESCRIPTION_MESSAGE);
        this.description = description;
    }

    public abstract void setStatus();

    @Override
    public abstract String getStatus();

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public String displayComments() {
        StringBuilder output = new StringBuilder();
        output.append(COMMENTS_HEADER);
        output.append("\n");
        comments.stream().forEach(e -> String.format("Author: %s%nDescription: %s%n", e.getAuthor(), e.getContent()));
        output.append(COMMENTS_HEADER);
        return null;
    }

    @Override
    public String historyOfChanges() {
        return historyOfChanges.getCompleteHistory();
    }

    @Override
    public String displayDetails() {
        return null;
    }
}
