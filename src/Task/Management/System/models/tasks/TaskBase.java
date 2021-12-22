package Task.Management.System.models.tasks;

import Task.Management.System.models.ChangesLoggerImpl;
import Task.Management.System.models.contracts.ChangesLogger;
import Task.Management.System.models.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.tasks.enums.Tasks;
import Task.Management.System.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.contracts.ChangesLogger.*;

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

    private final int id;
    private final List<Comment> comments;
    private final ChangesLogger historyOfChanges;
    private String title;
    private String description;


    public TaskBase(int id, Tasks tasksType, String title, String description) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        this.comments = new ArrayList<>();
        this.historyOfChanges = new ChangesLoggerImpl();

        addChangeToHistory(String.format(CREATION_MESSAGE,
                super.getClass().getSimpleName().replace("Base", ""),
                tasksType.toString()));
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        ValidationHelpers.validateIntRange(title.length(), TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, INVALID_NAME_MESSAGE);
        if (this.title == null) {
            this.title = title;
            return;
        }
        if (this.title.equals(title)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Title", this.title));
        }
        historyOfChanges.addChange(String.format(CHANGE_MESSAGE, "Title", this.title, title));
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        ValidationHelpers.validateIntRange(description.length(),
                DESCRIPTION_MIN_LENGTH,
                DESCRIPTION_MAX_LENGTH,
                INVALID_DESCRIPTION_MESSAGE);
        if (this.description == null) {
            this.description = description;
            return;
        }
        if (this.description.equals(description)) {
            throw new InvalidUserInput(String.format(IMPOSSIBLE_CHANGE_MESSAGE, "Description", this.description));
        }
        historyOfChanges.addChange(String.format(CHANGE_MESSAGE, "Description", this.description, description));
        this.description = description;
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
    public String displayComments() {
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
    public String getHistory() {
        return String.format("%s%n%s%s",
                HISTORY_HEADER,
                historyOfChanges.getCompleteHistory(),
                HISTORY_HEADER);
    }

    protected void addChangeToHistory(String description) {
        historyOfChanges.addChange(description);
    }

    @Override
    public String getChangeAt(int index) {
        return historyOfChanges.getChangeAt(index);
    }

    @Override
    public String displayDetails() {
        return String.format("ID: %d%n" +
                        "Title: %s%n" +
                        "Description: %s%n",
                getID(), getTitle(), getDescription());
    }
}
